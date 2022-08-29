package com.n9.model;

public class TinyUrlData {

    private String resourceid;
    private String resourcecollectionid;
    private String organizationid;
    private String name;
    private String description;
    private String refkey;
    private String fullurl;
    private String createdatetime;
    private String updatedatetime;
    private String entid;
    private String orgid;
    private String shorturl;

    public TinyUrlData() {

    }

    public TinyUrlData(String resourceid, String shorturl) {
        this.resourceid = resourceid;
        this.shorturl = shorturl;
    }

    public TinyUrlData(TinyUrlData tinyUrlData) {
        this.resourceid = tinyUrlData.resourceid;
        this.resourcecollectionid = tinyUrlData.resourcecollectionid;
        this.organizationid = tinyUrlData.organizationid;
        this.name = tinyUrlData.name;
        this.description = tinyUrlData.description;
        this.refkey = tinyUrlData.refkey;
        this.fullurl = tinyUrlData.fullurl;
        this.createdatetime = tinyUrlData.createdatetime;
        this.updatedatetime = tinyUrlData.updatedatetime;
        this.entid = tinyUrlData.entid;
        this.orgid = tinyUrlData.orgid;
        this.shorturl = tinyUrlData.shorturl;

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


    public String getResourcecollectionid() {
        return resourcecollectionid;
    }

    public void setResourcecollectionid(String resourcecollectionid) {
        this.resourcecollectionid = resourcecollectionid;
    }

    public String getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(String organizationid) {
        this.organizationid = organizationid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefkey() {
        return refkey;
    }

    public void setRefkey(String refkey) {
        this.refkey = refkey;
    }

    public String getFullurl() {
        return fullurl;
    }

    public void setFullurl(String fullurl) {
        this.fullurl = fullurl;
    }

    public String getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(String createdatetime) {
        this.createdatetime = createdatetime;
    }

    public String getUpdatedatetime() {
        return updatedatetime;
    }

    public void setUpdatedatetime(String updatedatetime) {
        this.updatedatetime = updatedatetime;
    }

    public String getEntid() {
        return entid;
    }

    public void setEntid(String entid) {
        this.entid = entid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Override
    public String toString() {
        return "TinyUrlData{" +
                "resourceid='" + resourceid + '\'' +
                ", shorturl='" + shorturl + '\'' +
                '}';
    }


}
