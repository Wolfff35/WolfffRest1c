package com.wolff.wolfffrest1c.tasks;

import android.os.AsyncTask;

import com.wolff.wolfffrest1c.rest.PatchData;
import com.wolff.wolfffrest1c.rest.PostData;



/**
 * Created by wolff on 23.02.2017.
 */

public class PatchDataTask extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        PatchData patchData = new PatchData();
        String data1C = patchData.patchDataOnServer(params[0],params[1],params[2],params[3]);
        return data1C;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}


