package com.qxy.DataStructure;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.base.ImageObject;
import com.bytedance.sdk.open.aweme.base.MediaContent;
import com.bytedance.sdk.open.aweme.base.VideoObject;
import com.bytedance.sdk.open.aweme.share.Share;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.DataStructure.bean.ClientToken;
import com.qxy.DataStructure.network.RetrofitClient;
import com.qxy.DataStructure.network.Service.UserService;
import com.qxy.DataStructure.utils.CommonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 抖音、tiktok share功能测试类
 * <p>
 * 注意：因为涉及clientkey及包名的鉴权
 * <p>
 * 此sdk demo并不能直接跑通整个分享流程；
 * <p>
 * 建议使用者直接在自己app内设置好clientkey等字段后接入调试;
 */
public class MainActivity extends AppCompatActivity {

    DouYinOpenApi douYinOpenApi;


    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    Button mShareToTikTok;

    EditText mMediaPathList;

    Button mAddMedia;

    Button mClearMedia;

    Button mRank;

    Button authbt;
    EditText mSetDefaultHashTag;


    static final int PHOTO_REQUEST_GALLERY = 10;

    int currentShareType;

    private ArrayList<String> mUri = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化api,需要传入targetApp,默认为TikTok
        douYinOpenApi = DouYinOpenApiFactory.create(this);

        mShareToTikTok = findViewById(R.id.share_to_tiktok);
        mSetDefaultHashTag = findViewById(R.id.set_default_hashtag);
        mMediaPathList = findViewById(R.id.media_text);
        mAddMedia = findViewById(R.id.add_photo_video);
        mClearMedia = findViewById(R.id.clear_media);
        authbt = findViewById(R.id.auth_bt);
        mRank = findViewById(R.id.rank);

        /**
         * 以下代码只方便测试
         */
        findViewById(R.id.go_to_system_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this, mPermissionList, 100);
            }
        });

        mAddMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSystemGallery();
            }
        });

        mClearMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUri.clear();
                mMediaPathList.setText("");
            }
        });
        mShareToTikTok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(currentShareType);
            }
        });
        authbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authorization.Request request = new Authorization.Request();
                request.scope = "user_info,trial.whitelist";    // 用户授权时必选权限
//                request.optionalScope1 = mOptionalScope2;     // 用户授权时可选权限（默认选择）
//                request.optionalScope0 = mOptionalScope1;    // 用户授权时可选权限（默认不选）
                request.state = "ww";                                   // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
                douYinOpenApi.authorize(request);               // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用wap页授权
            }
        });

        mRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RankActivity.class);
                startActivity(intent);
            }
        });

        HashMap<String,String> map =new HashMap<>();
        map.put("client_key", "awjrtgz1pvt3ahpn");
        map.put("client_secret", "0cbd75db007d0bdc5f636fe571159d17");
        map.put("grant_type", "client_credential");
        RetrofitClient.getInstance().getService(UserService.class)
                .getClientToken(CommonUtils.generateRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ClientToken>() {
                    @Override
                    public void accept(ClientToken clientToken) throws Throwable {
                        SharedPreferences sharedPreferences = getSharedPreferences("Client",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("clientToken",clientToken.getData().getAccess_token());
                        editor.commit();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Toast.makeText(MainActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    /**
     * share 功能示例代码
     *
     * @param shareType
     * @return
     */
    private boolean share(int shareType) {
        Share.Request request = new Share.Request();
        switch (shareType) {
            // 单图目前只有抖音支持，海外版不支持
            case Share.IMAGE:
                ImageObject imageObject = new ImageObject();
                imageObject.mImagePaths = mUri;
                MediaContent mediaContent = new MediaContent();
                mediaContent.mMediaObject = imageObject;
                request.mMediaContent = mediaContent;
                //携带话题
//                ArrayList<String> hashtags = new ArrayList<>();
//                hashtags.add("话题");
//                request.mHashTagList = hashtags;
                break;
            case Share.VIDEO:
                VideoObject videoObject = new VideoObject();
                videoObject.mVideoPaths = mUri;
                MediaContent content = new MediaContent();
                content.mMediaObject = videoObject;
                request.mMediaContent = content;
                //携带话题
//                ArrayList<String> hashtags = new ArrayList<>();
//                hashtags.add("话题");
//                request.mHashTagList = hashtags;
                //可以在mState传入shareId
                request.mState = "ss";

//                 可以通过callerLocalEntry设置自己接收回调的类，不必非得用TikTokEntryActivity
//                request.callerLocalEntry = "com.xxx.xxx...activity";
                //如果拥有默认话题权限，则可通过这个变量设置默认话题
//                request.mHashTag = "设置我的默认话题";

                // 0.0.1.1版本新增分享带入小程序功能，具体请看官网 对应抖音及tiktok版本6.7.0
//                TikTokMicroAppInfo mMicroInfo = new TikTokMicroAppInfo();
//                mMicroInfo.setAppTitle("小程序title");
//                mMicroInfo.setDescription("小程序描述");
//                mMicroInfo.setAppId("小程序id");
//                mMicroInfo.setAppUrl("小程序启动链接");
//                request.mMicroAppInfo = mMicroInfo;

                break;
        }

        return douYinOpenApi.share(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    openSystemGallery();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_GALLERY:
                    Uri uri = data.getData();
                    mUri.add(UriUtil.convertUriToPath(this, uri));
                    mMediaPathList.setVisibility(View.VISIBLE);
//                    mSetDefaultHashTag.setVisibility(View.VISIBLE);
                    mMediaPathList.setText(mMediaPathList.getText().append("\n").append(uri.getPath()));
                    mShareToTikTok.setVisibility(View.VISIBLE);
                    mAddMedia.setVisibility(View.VISIBLE);
                    mClearMedia.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void openSystemGallery() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.add_photo_video)
                .setNegativeButton(R.string.video, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentShareType = Share.VIDEO;
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("video/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                    }
                })
                .setPositiveButton(R.string.image, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentShareType = Share.IMAGE;
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}