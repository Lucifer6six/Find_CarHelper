package com.find_carhelper.bean;


public class ItemBean {

    private  String path;
    private  int position;
    private String name;
    private String code;
    private String photoUrl;
    private String thumPhotoUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getThumPhotoUrl() {
        return thumPhotoUrl;
    }

    public void setThumPhotoUrl(String thumPhotoUrl) {
        this.thumPhotoUrl = thumPhotoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
