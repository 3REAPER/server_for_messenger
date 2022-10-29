package com.pervukhin.domain;

public class Message {
    private int id;
    private String message;
    private String time;
    private String isEdit;
    private Profile authorId;

    private int conditionSend;

    public Message(int id, String message, String time, String isEdit, Profile authorId, int conditionSend) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.isEdit = isEdit;
        this.authorId = authorId;
        this.conditionSend = conditionSend;
    }

    public Message(String message, String time, String isEdit, Profile authorId, int conditionSend) {
        this.message = message;
        this.time = time;
        this.isEdit = isEdit;
        this.authorId = authorId;
        this.conditionSend = conditionSend;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public void setAuthorId(Profile authorId) {
        this.authorId = authorId;
    }

    public void setConditionSend(int conditionSend) {
        this.conditionSend = conditionSend;
    }
    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getIsEdit() {
        return isEdit;
    }
    public Profile getAuthor() {
        return authorId;
    }

    public int getConditionSend() {
        return conditionSend;
    }
}