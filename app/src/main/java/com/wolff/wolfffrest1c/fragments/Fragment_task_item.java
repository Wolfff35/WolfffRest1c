package com.wolff.wolfffrest1c.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wolff.wolfffrest1c.R;
import com.wolff.wolfffrest1c.objects.WTask;


/**
 * Created by wolff on 16.02.2017.
 */

public class Fragment_task_item extends Fragment {
   private Menu optionsMenu;
   private Boolean isNewTask = false;
   private Boolean isModifiedTask = false;
   private Boolean isEditTask = false;
   private WTask main_taskItem;

    EditText edName;
    EditText edId;
    EditText edGuid;
    EditText edAuthor;
    EditText edProgrammer;
    EditText edText;
    EditText edPs;
    EditText edDateCreated;
    EditText edDateInWork;
    EditText edDateClosed;
    CheckBox tvIsInWork;
    CheckBox tvIsClosed;
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          this.optionsMenu = menu;
        inflater.inflate(R.menu.fragment_task_item_options_menu, menu);
        super.onCreateOptionsMenu(optionsMenu, inflater);
        setOptionsMenuItemVisibility();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();
        switch (id){
            case R.id.action_delete:
                Log.e("MENU FRAGM","DELETE");
                break;
            case R.id.action_edit:
                Log.e("MENU FRAGM","EDIT");
                isEditTask=true;
                setFormElementVisibility();
                setOptionsMenuItemVisibility();
                break;
            case R.id.action_save:
                Log.e("MENU FRAGM","SAVE");
                break;
            case R.id.action_undo:
                break;
            default:
                Log.e("MENU FRAGM","DEFAULT");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        savedInstanceState = this.getArguments();
        if (savedInstanceState != null) {
            main_taskItem =(WTask)savedInstanceState.getSerializable("WTask");
             if(main_taskItem==null) {
                isNewTask=true;
                isModifiedTask =false;
                isEditTask = true;
            } else {
                 isNewTask=false;
                 isModifiedTask=false;
                 isEditTask=false;
             }
        }

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public static Fragment_task_item newInstance(WTask task){

        Fragment_task_item fragment = new Fragment_task_item();
        Bundle bundle = new Bundle();
        bundle.putSerializable("WTask",task);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_item,container, false);
        edAuthor = (EditText) view.findViewById(R.id.edAuthor);
        edName = (EditText) view.findViewById(R.id.edName);
        edId= (EditText) view.findViewById(R.id.edId);
        edGuid= (EditText) view.findViewById(R.id.edGuid);
        edProgrammer= (EditText) view.findViewById(R.id.edProgrammer);
        edText= (EditText) view.findViewById(R.id.edText);
        edPs= (EditText) view.findViewById(R.id.edPs);
        edDateCreated= (EditText) view.findViewById(R.id.edDateCreated);
        edDateInWork= (EditText) view.findViewById(R.id.edDateInWork);
        edDateClosed= (EditText) view.findViewById(R.id.edDateClosed);
        tvIsInWork = (CheckBox) view.findViewById(R.id.tvIsInWork);
        tvIsClosed = (CheckBox) view.findViewById(R.id.tvIsClosed);

         if(main_taskItem!=null) {
            if(main_taskItem.getAuthor()!=null) {
                edAuthor.setText(main_taskItem.getAuthor().getName());
            }
            edName.setText(main_taskItem.getName());
            edId.setText(main_taskItem.getId());
            edGuid.setText(main_taskItem.getGuid());

            if(main_taskItem.getProgrammer()!=null) {
                edProgrammer.setText(main_taskItem.getProgrammer().getName());
            }
            edText.setText(main_taskItem.getText());
            edPs.setText(main_taskItem.getPs());
            edDateCreated.setText(main_taskItem.getDateCreate().toString());
            edDateInWork.setText(main_taskItem.getDateInWork().toString());
            edDateClosed.setText(main_taskItem.getDateClosed().toString());
            tvIsInWork.setChecked(main_taskItem.isInWork());
            tvIsClosed.setChecked(main_taskItem.isClosed());

        }
        setFormElementVisibility();
        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                isModifiedTask=true;
                setOptionsMenuItemVisibility();
            }
        });
        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                isModifiedTask=true;
                setOptionsMenuItemVisibility();
            }
        });
        tvIsInWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isModifiedTask=true;
                setOptionsMenuItemVisibility();
            }
        });
        tvIsClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isModifiedTask=true;
                setOptionsMenuItemVisibility();
            }
        });
        return view;
    }
    public void setOptionsMenuItemVisibility(){
        Log.e("isModifiedTask = ",""+isModifiedTask);
        if(optionsMenu!=null) {
            MenuItem item_edit = optionsMenu.findItem(R.id.action_edit);
            item_edit.setVisible(!isEditTask);

            MenuItem item_save = optionsMenu.findItem(R.id.action_save);
            item_save.setVisible(isModifiedTask);
        }
    }
private  void setFormElementVisibility(){
     edName.setEnabled(isEditTask);
     edId.setEnabled(false);
     edGuid.setEnabled(false);
     edAuthor.setEnabled(false);
     edProgrammer.setEnabled(false);
     edText.setEnabled(isEditTask);
     edPs.setEnabled(isEditTask);
     edDateCreated.setEnabled(false);
     edDateInWork.setEnabled(false);
     edDateClosed.setEnabled(false);
    tvIsInWork.setEnabled(isEditTask);
    tvIsClosed.setEnabled(isEditTask);

}
}
