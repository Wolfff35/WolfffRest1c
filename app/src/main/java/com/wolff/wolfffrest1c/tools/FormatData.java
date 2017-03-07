package com.wolff.wolfffrest1c.tools;

import android.content.Context;
import android.util.Log;

import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.objects.WUsers;
import com.wolff.wolfffrest1c.rest.RESTInvoker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.wolff.wolfffrest1c.Const.DATE_FORMAT_STR;

/**
 * Created by wolff on 28.02.2017.
 */

public class FormatData {
/*    public String format_user_post(String userString, WUsers user){
        Convert convert = new Convert();
        String finStr = String.format(userString,
                                        convert.dateToString(new Date(),DATE_FORMAT_STR),
                                                user.getName(),
                                                "true",
                                                "false",
                                                user.getName(),
                                                "FALSE");

        Log.e("POST STR",""+finStr);
        return finStr;
    }
 */
    public String format_task_patch(Context context, WTask task){
        //{
        //    "odata.metadata": "http://13.10.12.10/v83_zadacha/odata/standard.odata/$metadata#Catalog_Пользователи/@Element",
        //        "Description": "?????"
        //}
        RESTInvoker restInvoker = new RESTInvoker();
        Convert convert = new Convert();
        JSONObject object = new JSONObject();
        try {
            object.put("odata.metadata",""+restInvoker.getBaseUrl(context)+"$metadata#Catalog_Tasks/@Element");
            object.put("Description",task.getName());
            object.put("Содержание",task.getText());
            object.put("Примечание",task.getPs());
            object.put("фЗавершена",task.isClosed());
            if((task.isClosed())&&(task.getDateClosed()!=null)) {
                object.put("ДатаЗавершения", convert.dateToString(task.getDateClosed(), DATE_FORMAT_STR));
            }
            object.put("фПринятаВРаботу",task.isInWork());
            if((task.isInWork())&&(task.getDateInWork()!=null)) {
                object.put("ДатаПринятияВРаботу", convert.dateToString(task.getDateInWork(), DATE_FORMAT_STR));
            }
        return object.toString();
        } catch (JSONException e) {
            Log.e("ERROE"," = "+e.getLocalizedMessage());
           return null;
        }
    }
    public String format_task_post(String taskString, WTask task){
        Convert convert = new Convert();
        WUsers lProg = task.getProgrammer();
        String lProgGuid;
        if(lProg!=null){
            lProgGuid=lProg.getGuid();
        }else {
            lProgGuid="";
        }
        String currDate = convert.dateToString(new Date(),DATE_FORMAT_STR);
        String finStr = String.format(taskString,
                                        currDate,
                                        task.getName(),
                                        task.getAuthor().getGuid(),
                                        lProgGuid,
                                        task.getText(),
                                        task.getPs(),
                                        currDate);
        //Log.e("POST STR",""+finStr);
        return finStr;
    }

    public WUsers getCurrentUser(ArrayList<WUsers>users,String userUID){
        for (int i=0;i<users.size();i++){
            if (users.get(i).getuID().equalsIgnoreCase(userUID)){
                return users.get(i);
            }
        }
        return null;

    }
}
