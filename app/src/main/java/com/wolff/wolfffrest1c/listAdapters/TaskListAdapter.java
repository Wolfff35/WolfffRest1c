package com.wolff.wolfffrest1c.listAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wolff.wolfffrest1c.R;
import com.wolff.wolfffrest1c.objects.WTask;

import java.util.ArrayList;

/**
 * Created by wolff on 08.02.2017.
 */

public class TaskListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<WTask> myTasks;

    public TaskListAdapter(Context context,ArrayList<WTask> myTasks){
        this.context = context;
        this.myTasks = myTasks;
        this.lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return myTasks.size();
    }

    @Override
    public WTask getItem(int position) {
        return myTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TextView tvDescribeTask;

        if(view==null){
            view = lInflater.inflate(R.layout.fragment_task_list_item_adapter,parent,false);
        }
        tvDescribeTask = (TextView) view.findViewById(R.id.tvDescribeTask);

        WTask myTask = getItem(position);
        tvDescribeTask.setText(myTask.getName());
        return view;

    }
}
