package com.michael.recyclerview;

import java.util.List;

/**
 * Created by chenyao on 2017/7/20.
 */

public class WorkbenchBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * info : [{"action":4,"clicknum":"0","img":"http://cdn-file.cheguo.com/files/2017-07-01/201707011657464328851.png","needauth":false,"needlogin":false,"order":1,"title":"新车分期"},{"action":1,"clicknum":"0","img":"http://cdn-file.cheguo.com/files/2017-07-01/201707011658171124833.png","needauth":true,"needlogin":true,"order":2,"title":"车贷还款"},{"action":0,"clicknum":"0","img":"http://cdn-file.cheguo.com/files/2017-07-01/201707011658396784808.png","needauth":false,"needlogin":false,"order":3,"title":"保单查询","url":"  http://m.cheguo.com/activities/carmoney?phone"},{"action":0,"clicknum":"0","img":"http://cdn-file.cheguo.com/files/2017-07-01/201707011659109919318.png","needauth":false,"needlogin":false,"order":4,"title":"违章查询","url":"http://10.10.13.7:9000/peccancyquery.html"},{"action":0,"clicknum":"0","img":"http://cdn-file.cheguo.com/files/2017-07-01/201707011659402913399.png","needauth":false,"needlogin":false,"newicon":"","order":5,"title":"车后服务","url":"http://market.qccr.com/carmanH5/store/page/list?channelCode=309"},{"action":7,"clicknum":"0","img":"http://cdn-file.cheguo.com/files/2017-07-01/201707011659567987224.png","needauth":false,"needlogin":false,"order":6,"title":"车主再分期"},{"action":0,"clicknum":"0","img":"http://cdn-file.cheguo.com/files/2017-07-01/201707011700122913442.png","needauth":false,"needlogin":false,"newicon":"","order":7,"title":"贷款流程","url":"http://10.10.13.7:5000/usedcar.html?showtype=2"},{"action":6,"clicknum":"0","img":"http://cdn-file.cheguo.com/files/2017-07-01/201707011700342375712.png","needauth":false,"needlogin":false,"newicon":"","order":8,"title":"更多"}]
         * title : 车价贷
         */

        private String title;
        private List<InfoBean> info;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * action : 4
             * clicknum : 0
             * img : http://cdn-file.cheguo.com/files/2017-07-01/201707011657464328851.png
             * needauth : false
             * needlogin : false
             * order : 1
             * title : 新车分期
             * url :   http://m.cheguo.com/activities/carmoney?phone
             * newicon :
             */

            private int action;
            private String clicknum;
            private String img;
            private boolean needauth;
            private boolean needlogin;
            private int order;
            private String title;
            private String url;
            private String newicon;

            public int getAction() {
                return action;
            }

            public void setAction(int action) {
                this.action = action;
            }

            public String getClicknum() {
                return clicknum;
            }

            public void setClicknum(String clicknum) {
                this.clicknum = clicknum;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public boolean isNeedauth() {
                return needauth;
            }

            public void setNeedauth(boolean needauth) {
                this.needauth = needauth;
            }

            public boolean isNeedlogin() {
                return needlogin;
            }

            public void setNeedlogin(boolean needlogin) {
                this.needlogin = needlogin;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getNewicon() {
                return newicon;
            }

            public void setNewicon(String newicon) {
                this.newicon = newicon;
            }
        }
    }
}
