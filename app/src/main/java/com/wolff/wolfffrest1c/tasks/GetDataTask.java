package com.wolff.wolfffrest1c.tasks;

import android.os.AsyncTask;

import com.wolff.wolfffrest1c.rest.GetData;


/**
 * Created by wolff on 22.02.2017.
 */

public class GetDataTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            GetData getData = new GetData();
            String data1C = getData.getDataFromServer(params[0]);
           // Log.e("=GetXMLFromServer1C","params[0] = "+params[0]);
              return data1C;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

