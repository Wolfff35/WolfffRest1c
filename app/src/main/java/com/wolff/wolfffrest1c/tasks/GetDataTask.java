package com.wolff.wolfffrest1c.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wolff.wolfffrest1c.rest.GetData;

import java.io.IOException;


/**
 * Created by wolff on 22.02.2017.
 */

public class GetDataTask extends AsyncTask<String,Void,String> {
        private Context mContext;
        public GetDataTask(Context context){
            mContext=context;
        }
        @Override
        protected String doInBackground(String... params) {
            GetData getData = new GetData();
            String data1C = null;
            try {
                data1C = getData.getDataFromServer(mContext,params[0],params[1]);
            } catch (IOException e) {
                e.printStackTrace();
                data1C=null;
            }
            //Log.e("=GetDataTask","params[0] = "+params[0]);
            //Log.e("=GetDataTask","Get = "+data1C);
              return data1C;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast toast;
            if(s!=null) {
                toast = Toast.makeText(mContext, "Данные обновлены", Toast.LENGTH_LONG);
            }else {
                toast = Toast.makeText(mContext, "Не получилось обновить данные с сервера", Toast.LENGTH_LONG);
            }
            toast.show();

        }
    }

