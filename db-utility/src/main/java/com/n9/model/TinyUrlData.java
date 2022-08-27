package com.n9.model;

public class TinyUrlData {

    private String resourceid;

    private String shorturl;

    public TinyUrlData(){

    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getShorturl() {
        return shorturl;
    }

    public void setShorturl(String shorturl) {
        this.shorturl = shorturl;
    }

    @Override
    public String toString() {
        return "TinyUrlData{" +
                "resourceid='" + resourceid + '\'' +
                ", shorturl='" + shorturl + '\'' +
                '}';
    }
}
