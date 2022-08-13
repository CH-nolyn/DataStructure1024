package com.qxy.DataStructure.bean;

public class ClientToken {
    private DataDTO data;
    private String message;

    @Override
    public String toString() {
        return "ClientToken{" +
                "data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataDTO {
        private String access_token;
        private String captcha;
        private String desc_url;
        private String description;
        private int error_code;
        private int expires_in;
        private String log_id;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getCaptcha() {
            return captcha;
        }

        public void setCaptcha(String captcha) {
            this.captcha = captcha;
        }

        public String getDesc_url() {
            return desc_url;
        }

        public void setDesc_url(String desc_url) {
            this.desc_url = desc_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }
    }
}
