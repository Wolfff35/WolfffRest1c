package com.wolff.wolfffrest1c.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.wolff.wolfffrest1c.Const.READ_TIMEOUT;
import static com.wolff.wolfffrest1c.Const.CONNECT_TIMEOUT;

/**
 * Created by wolff on 22.02.2017.
 */

public class GetData {

    public String getDataFromServer(Context context, String catalog,String guidCurrentUser) throws IOException {
        RESTInvoker restInvoker = new RESTInvoker();
        StringBuilder sb = new StringBuilder();
            URL url = new URL(restInvoker.getBaseUrl(context) + catalog+"?$format=json"+getFiltersForQuery(context,catalog,guidCurrentUser));
            HttpURLConnection urlConnection = restInvoker.getConnection(context,url);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            urlConnection.disconnect();
            //Log.e("GET",""+sb.toString());
            return sb.toString();
     }

    private String getFiltersForQuery(Context context,String catalog,String guidCurrentUser){
        //&$filter=DeletionMark eq false -  не помеченные на удаление
        //&$filter=фЗавершена eq false - не завершенные
        //&$filter=Автор_Key eq guid'4b1b55a1-bc6d-11e6-80c2-f2bd425ab9dd' = автор
        //&$filter=Исполнитель_Key eq guid'4b1b55a1-bc6d-11e6-80c2-f2bd425ab9dd' = программист

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean iAmAuthor = sp.getBoolean("iAmAuthor",false);
        boolean iAmProgrammer = sp.getBoolean("iAmProgrammer",false);
        boolean notDeleted = sp.getBoolean("notDeleted",false);
        boolean notFinished = sp.getBoolean("notFinished",false);
        //boolean hasFilter = false;
        boolean isFirst=false;
        StringBuffer sb = new StringBuffer();

        if(catalog.equalsIgnoreCase("Catalog_Пользователи/")){
        return "";
       }else if(catalog.equalsIgnoreCase("Catalog_Tasks/")){
            if(iAmAuthor|iAmProgrammer|notDeleted|notFinished){
                //hasFilter=true;
                sb.append("&$filter=");
            }else{
                return "";
            }
            if (iAmAuthor){
                isFirst=true;
                sb.append("Автор_Key eq guid'"+guidCurrentUser+"'");
            }
            if (iAmProgrammer){
                if(isFirst){
                    sb.append(" and ");
                }
                sb.append("Исполнитель_Key eq guid'"+guidCurrentUser+"'");
            }
            if (notDeleted){
                if(isFirst){
                    sb.append(" and ");
                }
                sb.append("DeletionMark eq false");
            }
            if (notFinished){
                if(isFirst){
                    sb.append(" and ");
                }
                sb.append("фЗавершена eq false");
            }
            //Log.e("GET FILTER",""+sb.toString());
            return  sb.toString();
       }
        return "";
    }

}
