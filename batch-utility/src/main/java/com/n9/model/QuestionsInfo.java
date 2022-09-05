package com.n9.model;

public class QuestionsInfo {

    //questionid,qtopicid,summary,body,refkey,createdatetime,updatedatetime,entid,orgid
    private String questionid;
    private String qtopicid;
    private String summary;
    private String body;
    private String refkey;
    private String createdatetime;
    private String updatedatetime;
    private String entid;
    private String orgid;

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getQtopicid() {
        return qtopicid;
    }

    public void setQtopicid(String qtopicid) {
        this.qtopicid = qtopicid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRefkey() {
        return refkey;
    }

    public void setRefkey(String refkey) {
        this.refkey = refkey;
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
        return "QuestionsInfo{" +
                "questionid='" + questionid + '\'' +
                ", summary='" + summary + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
