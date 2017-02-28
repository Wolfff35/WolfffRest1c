package com.wolff.wolfffrest1c.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.wolff.wolfffrest1c.R;
import com.wolff.wolfffrest1c.jSon.JsonParser;
import com.wolff.wolfffrest1c.listAdapters.TaskListAdapter;
import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.objects.WUsers;
import com.wolff.wolfffrest1c.tasks.GetDataTask;
import com.wolff.wolfffrest1c.tasks.PatchDataTask;
import com.wolff.wolfffrest1c.tasks.PostDataTask;
import com.wolff.wolfffrest1c.tools.Convert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by wolff on 07.02.2017.
 */

public class Fragment_task_list extends ListFragment {
    private FragmentTaskListListener listener;
    TaskListAdapter taskListAdapter;
    ArrayList<WTask> main_taskList;
    ArrayList<WUsers> main_userList;

     @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentTaskListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Context context = getActivity().getApplicationContext();
        taskListAdapter= new TaskListAdapter(context,main_taskList);
        setListAdapter(taskListAdapter);
        getListView().setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onTaskSelected(main_taskList.get(i));
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetDataTask getDataTask1 =new GetDataTask(getActivity().getApplicationContext());
        GetDataTask getDataTask2 =new GetDataTask(getActivity().getApplicationContext());
        try {
            String data1CSrv1 = getDataTask1.execute("Catalog_Пользователи/").get();
            JsonParser parser = new JsonParser();
            main_userList=parser.getUserListFromServerData(data1CSrv1);

            String data1CSrv2 = getDataTask2.execute("Catalog_Tasks/").get();
            main_taskList=parser.getTaskListFromServerData(data1CSrv2,main_userList);
        } catch (InterruptedException e) {
            Log.e("ERR"," = 1");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("ERR"," = 2");
            e.printStackTrace();
        }
        Convert convert = new Convert();
 /*       InputStream is = getResources().openRawResource(R.raw.post_query_user);
        String ss2 = convert.getStringFromInputStream(is);
       PostDataTask pdt = new PostDataTask();
        pdt.execute(ss2,"Catalog_Пользователи");
*/
   /*     InputStream inputStream = getResources().openRawResource(R.raw.patch_query_task2);
        String ss3 = convert.getStringFromInputStream(inputStream);
        PatchDataTask patchDataTask = new PatchDataTask();
        String dataVersion = "AAAAAAAARmw";
        patchDataTask.execute(ss3,"Catalog_Пользователи","f4f6d96a-f9ce-11e6-80cb-f2bd425ab9dd",dataVersion);
*/
    }


    public interface FragmentTaskListListener{
    void onTaskSelected(WTask task);

    }
}
