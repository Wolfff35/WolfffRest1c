package com.wolff.wolfffrest1c.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wolff.wolfffrest1c.R;

/**
 * Created by wolff on 16.02.2017.
 */

public class Fragment_task_item extends Fragment {
    Menu optionsMenu;
    Boolean isNewTask = false;
    Boolean isModifiedTask = false;
    Boolean isEditTask = false;
    String guid;

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          this.optionsMenu = menu;
        inflater.inflate(R.menu.fragment_task_item_options_menu, menu);
        super.onCreateOptionsMenu(optionsMenu, inflater);
    //Log.e("onCreateOptionsMenu","onCreateOptionsMenu");
        setOptionsMenuItemVisibility();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        savedInstanceState = this.getArguments();
        if (savedInstanceState != null) {
            guid = savedInstanceState.getString("guid");
            //Log.e("guid = ",""+guid);
            if(guid.equalsIgnoreCase("NEW")) {
                isNewTask=true;
                isModifiedTask =false;
                isEditTask = true;
            }
        }

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        //Log.e("onCreate","onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.e("onActivityCreated","onActivityCreated");
    }
    public static Fragment_task_item newInstance(String guid){
        //Log.e("newInstance","newInstance");

        Fragment_task_item fragment = new Fragment_task_item();
        Bundle bundle = new Bundle();
        bundle.putString("guid", guid);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_item,container, false);
        //Log.e("onCreateView","onCreateView");

        //       TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
 //       TextView tvDescribe = (TextView) view.findViewById(R.id.tvDescribe);
 //       ImageView ivLogo = (ImageView) view.findViewById(R.id.ivLogo);

        //long idChannel= this.getArguments().getLong("idChannel");
 //       long idNews= this.getArguments().getLong("idNews");
 //       DBConnector dbConnector = new DBConnector(getActivity().getApplicationContext());
 //       dbConnector.open();
 //       Cursor cursor = dbConnector.news_getById_cursor(idNews);
 //       Log.e("CURR","count = "+cursor.getCount());
 //       cursor.moveToFirst();
 //       WNews news = dbConnector.getNews(cursor);
 //       tvTitle.setText(news.getTitle());
 //       Log.e("DESC",""+news.getDescription());
 //       //String desc= news.getDescription().replaceAll("\\p{Cntrl}", "");
        //tvDescribe.setText(desc);
 ///       dbConnector.close();
        return view;
    }
    public void setOptionsMenuItemVisibility(){
        //Log.e("OPTMENU_VIS","SETTING VISIBILITY ");//+optionsMenu.toString());
        if(optionsMenu!=null) {
            MenuItem item_edit = optionsMenu.findItem(R.id.action_edit);
            item_edit.setVisible(!isEditTask);

            MenuItem item_save = optionsMenu.findItem(R.id.action_save);
            item_save.setVisible(isModifiedTask);
        }
    }

}
