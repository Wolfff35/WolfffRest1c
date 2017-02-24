package com.wolff.wolfffrest1c.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wolff on 14.02.2017.
 */

public class WUsers extends WCatalog {

    public WUsers(JSONObject obj){
        try {
            this.setGuid(obj.getString("Ref_Key"));
            this.setId(obj.getString("Code"));
            this.setName(obj.getString("Description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
     }
}
