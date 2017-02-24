package com.wolff.wolfffrest1c.rest;

import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.wolff.wolfffrest1c.Const.BASE_URL;

/**
 * Created by wolff on 22.02.2017.
 */

public class PostData {
    public String postDataToServer(String data,String catalog){
       int responseCode=0;
        HttpURLConnection conn=null;
        RESTInvoker restInvoker = new RESTInvoker();
        try {
             URL url = new URL(BASE_URL+catalog);
            conn = restInvoker.getConnection(url);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));

            OutputStream os= new BufferedOutputStream(conn.getOutputStream());
            os.write(data.getBytes());
            os.flush();
            conn.connect();

            responseCode= conn.getResponseCode();
            //Log.e("!!!!!!","code== "+responseCode);
            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //InputStream is;
            if ((responseCode >= 200)&&(responseCode<=203)) {
                //is = conn.getInputStream();
                Log.e("SUCCESS","code== "+responseCode);
            }else{
                //is = conn.getErrorStream();
                Log.e("ERROR","code== "+responseCode);
            }
/*            byte[] buffer = new byte[8192]; // Такого вот размера буфер
            // Далее, например, вот так читаем ответ
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] dat = baos.toByteArray();
            String resultString = new String(dat, "UTF-8");
            Log.e("resultString",""+resultString);
*/
         } catch (MalformedURLException e) {
            //Log.e("!!!!!!"," 2 ");
        } catch (IOException e) {
            //Log.e("!!!!!!"," 3==== "+e.getLocalizedMessage()+"\n"+e.getMessage()+"\n"+e.fillInStackTrace().toString());
        }finally {

            conn.disconnect();

        }

        return  Integer.toString(responseCode);
    }


}
