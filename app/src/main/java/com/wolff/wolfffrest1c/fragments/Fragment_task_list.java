package com.wolff.wolfffrest1c.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;

import com.wolff.wolfffrest1c.R;
import com.wolff.wolfffrest1c.jSon.JsonParser;
import com.wolff.wolfffrest1c.listAdapters.TaskListAdapter;
import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.objects.WUsers;
import com.wolff.wolfffrest1c.tasks.GetDataTask;

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
    WUsers main_author;
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
        int[] colors = {0,ContextCompat.getColor(context,R.color.colorPrimary),0};
        getListView().setDivider(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors));
        getListView().setDividerHeight(5);
        getListView().setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onTaskSelected(main_taskList.get(i),main_author);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        main_userList = (ArrayList<WUsers>) args.getSerializable("UserList");
        main_author = (WUsers) args.getSerializable("Author");

         GetDataTask getDataTask2 =new GetDataTask(getActivity().getApplicationContext());
        try {
            JsonParser parser = new JsonParser();
            String data1CSrv2 = getDataTask2.execute("Catalog_Tasks/",main_author.getGuid()).get();
            main_taskList = parser.getTaskListFromServerData(data1CSrv2, main_userList);
            //Log.e("onCreateFRAGM",""+main_taskList.size()+", "+main_taskList.toString());
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }


    public interface FragmentTaskListListener{
    void onTaskSelected(WTask task, WUsers author);

    }
}
