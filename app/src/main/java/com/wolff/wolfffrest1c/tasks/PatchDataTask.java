package com.wolff.wolfffrest1c.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wolff.wolfffrest1c.rest.PatchData;
import com.wolff.wolfffrest1c.rest.PostData;



/**
 * Created by wolff on 23.02.2017.
 */

public class PatchDataTask extends AsyncTask<String,Void,String> {
    private Context mContext;
    public PatchDataTask(Context context){
        mContext=context;
    }
    @Override
    protected String doInBackground(String... params) {
        PatchData patchData = new PatchData();
        String data1C = patchData.patchDataOnServer(mContext,params[0],params[1],params[2],params[3]);
        Log.e("PatchDataTask",""+data1C);
        return data1C;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast toast;
        if(s.equalsIgnoreCase("200")) {
            toast = Toast.makeText(mContext, "Запись успешно сохранена", Toast.LENGTH_LONG);
        }else {
            toast = Toast.makeText(mContext, "Не удалось сохранить запись", Toast.LENGTH_LONG);
        }
        toast.show();
        //Log.e("PATCH TASK"," SUCESS - "+ s);
    }
}


