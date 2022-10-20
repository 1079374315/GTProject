package com.example.gtproject.model.http.bean;

import java.util.List;

public class JsonRootBean {
    private int status;
    private String message;
    private String request_id;
    private Result result;

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
    public String getRequest_id() {
        return request_id;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "JsonRootBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", request_id='" + request_id + '\'' +
                ", result=" + result +
                '}';
    }



    public static class Result {

        private Object location;
        private String address;
        private Object formatted_addresses;
        private Object address_component;
        private Object ad_info;
        private Object address_reference;
        private int poi_count;
        private List<Object> pois;

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getFormatted_addresses() {
            return formatted_addresses;
        }

        public void setFormatted_addresses(Object formatted_addresses) {
            this.formatted_addresses = formatted_addresses;
        }

        public Object getAddress_component() {
            return address_component;
        }

        public void setAddress_component(Object address_component) {
            this.address_component = address_component;
        }

        public Object getAd_info() {
            return ad_info;
        }

        public void setAd_info(Object ad_info) {
            this.ad_info = ad_info;
        }

        public Object getAddress_reference() {
            return address_reference;
        }

        public void setAddress_reference(Object address_reference) {
            this.address_reference = address_reference;
        }

        public int getPoi_count() {
            return poi_count;
        }

        public void setPoi_count(int poi_count) {
            this.poi_count = poi_count;
        }

        public List<Object> getPois() {
            return pois;
        }

        public void setPois(List<Object> pois) {
            this.pois = pois;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "location=" + location +
                    ", address='" + address + '\'' +
                    ", formatted_addresses=" + formatted_addresses +
                    ", address_component=" + address_component +
                    ", ad_info=" + ad_info +
                    ", address_reference=" + address_reference +
                    ", poi_count=" + poi_count +
                    ", pois=" + pois +
                    '}';
        }
    }

}
