package com.wolff.wolfffrest1c.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by wolff on 14.02.2017.
 */

public class WUsers extends WCatalog implements Serializable {
    private static final long serialVersionUID = 2163054469251704396L;

    private boolean isUser;
    private boolean isProgrammer;
    private String uID;
    private boolean isGod;

    public WUsers(JSONObject obj){
        try {
            this.setGuid(obj.getString("Ref_Key"));
            this.setId(obj.getString("Code"));
            this.setName(obj.getString("Description"));
            this.setuID(obj.getString("УникальныйИД"));
            this.setDeletionMark(obj.getBoolean("DeletionMark"));
            this.setGod(obj.getBoolean("фБог"));
            this.setProgrammer(obj.getBoolean("фПрограммист"));
            this.setUser(obj.getBoolean("фПользователь"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public WUsers(String ref_key,String code,String description){
        this.setGuid(ref_key);
        this.setId(code);
        this.setName(description);
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public boolean isProgrammer() {
        return isProgrammer;
    }

    public void setProgrammer(boolean programmer) {
        isProgrammer = programmer;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public boolean isGod() {
        return isGod;
    }

    public void setGod(boolean god) {
        isGod = god;
    }

 }
