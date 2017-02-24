package com.wolff.wolfffrest1c.jSon;

import android.util.Log;

import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.objects.WUsers;
import com.wolff.wolfffrest1c.tools.Convert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 22.02.2017.
 */

public class JsonParser {

    public ArrayList<WTask> getTaskListFromServerData(String serverData,ArrayList<WUsers> users){
        String separator = "value";
        ArrayList<WTask> temp = new ArrayList<>();
        Convert convert = new Convert();
        try {
            JSONObject dataJsonObj= new JSONObject(serverData);
            JSONArray myTasks = dataJsonObj.getJSONArray(separator);
            for(int i=0;i<=myTasks.length();i++){
                 JSONObject obj = myTasks.getJSONObject(i);
                WUsers author = convert.getUserByGuid(obj.getString("Автор_Key"),users);
                WUsers programmer = convert.getUserByGuid(obj.getString("Исполнитель_Key"),users);
                WTask newTask = new WTask(obj.getString("Ref_Key"),
                                            obj.getString("Code"),
                                            obj.getString("Description"),
                                            author, programmer,
                                            obj.getString("Содержание"),
                                            obj.getString("Примечание"),
                                            obj.getBoolean("фЗавершена"),
                                            convert.getDateFromString(obj.getString("ДатаЗавершения")),
                                            obj.getBoolean("фПринятаВРаботу"),
                                            convert.getDateFromString(obj.getString("ДатаПринятияВРаботу")),
                                            convert.getDateFromString(obj.getString("ДатаСоздания")));
                temp.add(newTask);
            }
        } catch (JSONException e) {
            e.getLocalizedMessage();
        }
        return temp;
    }
    public ArrayList<WUsers> getUserListFromServerData(String serverData){
        String separator = "value";
        ArrayList<WUsers> temp = new ArrayList<>();
        try {
            JSONObject dataJsonObj= new JSONObject(serverData);
            JSONArray myTasks = dataJsonObj.getJSONArray(separator);
            for(int i=0;i<=myTasks.length();i++){
                temp.add(new WUsers(myTasks.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.getLocalizedMessage();
        }
        return temp;
    }

}
