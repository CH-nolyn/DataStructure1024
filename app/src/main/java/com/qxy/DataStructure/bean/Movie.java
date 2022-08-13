package com.qxy.DataStructure.bean;

import java.util.List;

public class Movie {

    private DataDTO data;
    private ExtraDTO extra;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public ExtraDTO getExtra() {
        return extra;
    }

    public void setExtra(ExtraDTO extra) {
        this.extra = extra;
    }

    public static class DataDTO {
        private String active_time;
        private String description;
        private int error_code;
        private List<ListDTO> list;

        public String getActive_time() {
            return active_time;
        }

        public void setActive_time(String active_time) {
            this.active_time = active_time;
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

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class ListDTO {
            private List<String> actors;
            private List<String> directors;
            private int discussion_hot;
            private int hot;
            private String id;
            private int influence_hot;
            private String maoyan_id;
            private String name;
            private String name_en;
            private String poster;
            private String release_date;
            private int search_hot;
            private int topic_hot;
            private int type;
            private List<String> areas;
            private List<String> tags;

            public List<String> getActors() {
                return actors;
            }

            public void setActors(List<String> actors) {
                this.actors = actors;
            }

            public List<String> getDirectors() {
                return directors;
            }

            public void setDirectors(List<String> directors) {
                this.directors = directors;
            }

            public int getDiscussion_hot() {
                return discussion_hot;
            }

            public void setDiscussion_hot(int discussion_hot) {
                this.discussion_hot = discussion_hot;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getInfluence_hot() {
                return influence_hot;
            }

            public void setInfluence_hot(int influence_hot) {
                this.influence_hot = influence_hot;
            }

            public String getMaoyan_id() {
                return maoyan_id;
            }

            public void setMaoyan_id(String maoyan_id) {
                this.maoyan_id = maoyan_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName_en() {
                return name_en;
            }

            public void setName_en(String name_en) {
                this.name_en = name_en;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public String getRelease_date() {
                return release_date;
            }

            public void setRelease_date(String release_date) {
                this.release_date = release_date;
            }

            public int getSearch_hot() {
                return search_hot;
            }

            public void setSearch_hot(int search_hot) {
                this.search_hot = search_hot;
            }

            public int getTopic_hot() {
                return topic_hot;
            }

            public void setTopic_hot(int topic_hot) {
                this.topic_hot = topic_hot;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public List<String> getAreas() {
                return areas;
            }

            public void setAreas(List<String> areas) {
                this.areas = areas;
            }

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }
        }
    }

    public static class ExtraDTO {
        private String logid;
        private long now;

        public String getLogid() {
            return logid;
        }

        public void setLogid(String logid) {
            this.logid = logid;
        }

        public long getNow() {
            return now;
        }

        public void setNow(long now) {
            this.now = now;
        }
    }
}
