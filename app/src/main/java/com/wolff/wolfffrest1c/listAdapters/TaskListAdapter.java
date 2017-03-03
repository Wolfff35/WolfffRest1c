package com.wolff.wolfffrest1c.listAdapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wolff.wolfffrest1c.R;
import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.tools.Convert;

import java.util.ArrayList;

import static com.wolff.wolfffrest1c.Const.DATE_FORMAT_VID;

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
        if(myTasks!=null) {
            return myTasks.size();
        }else{
            return 0;
        }
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
        TextView tvDescribeTaskItem;
        TextView tvDopinfoTaskItem;

        if(view==null){
            view = lInflater.inflate(R.layout.fragment_task_list_item_adapter,parent,false);
        }
        tvDescribeTaskItem = (TextView) view.findViewById(R.id.tvDescribeTaskItem);
       tvDopinfoTaskItem = (TextView) view.findViewById(R.id.tvDopInfoTaskItem);

        WTask myTask = getItem(position);
                String label = formatListItem(myTask);
  //      tvDescribeTaskItem.setText(Html.fromHtml(label));
        if(myTask.isClosed()){
            tvDescribeTaskItem.setTypeface(null, Typeface.ITALIC);
        }
        if(myTask.isInWork()){
            tvDescribeTaskItem.setTypeface(null, Typeface.BOLD);
        }
        tvDescribeTaskItem.setText(Html.fromHtml(label));
        Convert convert = new Convert();
        tvDopinfoTaskItem.setText("Автор: "+myTask.getAuthor().getName()+", Дата создания:"+convert.dateToString(myTask.getDateCreate(),DATE_FORMAT_VID));
        return view;

    }
    private String formatListItem(WTask task){
        //StringBuilder sb = new StringBuilder();
        if(task.isDeletionMark()){
            return "<strike>"+task.getName()+"</strike>";
        }else{
            return task.getName();
        }
        //return sb.toString();
    }
}
