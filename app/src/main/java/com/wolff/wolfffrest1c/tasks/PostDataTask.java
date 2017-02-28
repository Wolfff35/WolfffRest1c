package com.wolff.wolfffrest1c.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.wolff.wolfffrest1c.rest.PostData;

/**
 * Created by wolff on 23.02.2017.
 */

public class PostDataTask extends AsyncTask<String,Void,String>{
    private Context mContext;
    public PostDataTask(Context context){
        mContext=context;
    }
    @Override
        protected String doInBackground(String... params) {
            PostData postData = new PostData();
            String data1C = postData.postDataToServer(mContext,params[0],params[1]);
            return data1C;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


