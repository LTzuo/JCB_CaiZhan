package com.cjkj.jcb_caizhan.entity;

import java.util.List;

/**
 * Created by 1 on 2018/1/16.
 * 测试实体模型
 */
public class TestInfo {
    private String  status;
    private String  msg;
    private List<TestWeatherInfo> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TestWeatherInfo> getResult() {

        return result;
    }

    public void setResult(List<TestWeatherInfo> result) {
        this.result = result;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class TestWeatherInfo {

        private String  name;
        private String  parentid;
        private String  parentname;
        private String  areacode;
        private String  zipcode;
        private String  depth;
        private String  id;

        public String getParentname() {
            return parentname;
        }

        public void setParentname(String parentname) {
            this.parentname = parentname;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepth() {
            return depth;
        }

        public void setDepth(String depth) {
            this.depth = depth;
        }

        public String getId() {

            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


    }
}
