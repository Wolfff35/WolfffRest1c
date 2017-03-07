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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.wolff.wolfffrest1c.R;
import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.objects.WUsers;
import com.wolff.wolfffrest1c.tasks.PatchDataTask;
import com.wolff.wolfffrest1c.tasks.PostDataTask;
import com.wolff.wolfffrest1c.tools.Convert;
import com.wolff.wolfffrest1c.tools.FormatData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import static android.R.layout.simple_list_item_1;
import static com.wolff.wolfffrest1c.Const.DATE_FORMAT_VID;


/**
 * Created by wolff on 16.02.2017.
 */

public class Fragment_task_item extends Fragment {
   private Menu optionsMenu;
   private Boolean isNewTask = false;
   private Boolean isModifiedTask = false;
   private Boolean isEditTask = false;
   private WTask main_taskItem;
    private ArrayList<WUsers> main_userList;
    private WUsers main_currentUser;
    EditText edName;
    EditText edId;
    EditText edGuid;
    EditText edAuthor;
    Spinner edProgrammer;
    EditText edText;
    EditText edPs;
    EditText edDateCreated;
    EditText edDateInWork;
    EditText edDateClosed;
    CheckBox tvIsInWork;
    CheckBox tvIsClosed;

    Convert convert = new Convert();
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
            case R.id.action_undo:
                break;
            case R.id.action_save:
                Log.e("MENU FRAGM","SAVE");
                saveTaskToServer();
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
            main_currentUser = (WUsers) savedInstanceState.getSerializable("WUsers");
            //Bundle args = getArguments();
            main_userList = (ArrayList<WUsers>) savedInstanceState.getSerializable("UserList");
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

    public static Fragment_task_item newInstance(WTask task, WUsers author,ArrayList<WUsers> userList){

        Fragment_task_item fragment = new Fragment_task_item();
        Bundle bundle = new Bundle();
        bundle.putSerializable("WTask",task);
        bundle.putSerializable("WUsers",author);
        bundle.putSerializable("UserList",userList);
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
        edProgrammer= (Spinner) view.findViewById(R.id.edProgrammer);
        edText= (EditText) view.findViewById(R.id.edText);
        edPs= (EditText) view.findViewById(R.id.edPs);
        edDateCreated= (EditText) view.findViewById(R.id.edDateCreated);
        edDateInWork= (EditText) view.findViewById(R.id.edDateInWork);
        edDateClosed= (EditText) view.findViewById(R.id.edDateClosed);
        tvIsInWork = (CheckBox) view.findViewById(R.id.tvIsInWork);
        tvIsClosed = (CheckBox) view.findViewById(R.id.tvIsClosed);

        String[] proggramers = new String[main_userList.size()];
       //main_userList.toArray(proggramers);
        int i=0;
        for(WUsers us:main_userList){
            proggramers[i]=us.getName();i++;
        }
          //ArrayAdapter<String> adapter  =  new  ArrayAdapter<String>(getActivity(), simple_list_item_1, new  String[]{"Рыжик", "Барсик", "Мурзик"});
        ArrayAdapter<String> adapter  =  new  ArrayAdapter<String>(getActivity(), simple_list_item_1, proggramers);
        edProgrammer.setAdapter(adapter);

       CompoundButton.OnCheckedChangeListener ochlIsClosed = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edDateClosed.setText(convert.dateToString(new Date(),DATE_FORMAT_VID));
                }else{
                    edDateClosed.setText("");
                }
            }
        };
        tvIsClosed.setOnCheckedChangeListener(ochlIsClosed);
        CompoundButton.OnCheckedChangeListener ochlIsInWork = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edDateInWork.setText(convert.dateToString(new Date(),DATE_FORMAT_VID));
                }else{
                    edDateInWork.setText("");
                }
            }
        };
        tvIsInWork.setOnCheckedChangeListener(ochlIsInWork);

         if((main_taskItem!=null)) {
            if(main_taskItem.getAuthor()!=null) {
                edAuthor.setText(main_taskItem.getAuthor().getName());
            }
            edName.setText(main_taskItem.getName());
            edId.setText(main_taskItem.getId());
            edGuid.setText(main_taskItem.getGuid());

            if(main_taskItem.getProgrammer()!=null) {
                //TODO edProgrammer.setText(main_taskItem.getProgrammer().getName());
            }
            edText.setText(main_taskItem.getText());
            edPs.setText(main_taskItem.getPs());
            Date zeroDate = new Date(1,1,1,0,0,0);
             edDateCreated.setText(convert.dateToString(main_taskItem.getDateCreate(),DATE_FORMAT_VID));
            if((main_taskItem.getDateInWork()!=null)&&(main_taskItem.getDateInWork().getTime()>zeroDate.getTime())) {
                edDateInWork.setText(convert.dateToString(main_taskItem.getDateInWork(), DATE_FORMAT_VID));
            }
            if((main_taskItem.getDateClosed()!=null)&&(main_taskItem.getDateClosed().getTime()>zeroDate.getTime())) {
                edDateClosed.setText(convert.dateToString(main_taskItem.getDateClosed(), DATE_FORMAT_VID));
            }
            tvIsInWork.setChecked(main_taskItem.isInWork());
            tvIsClosed.setChecked(main_taskItem.isClosed());

        }else {
             edDateCreated.setText(convert.dateToString(new Date(),DATE_FORMAT_VID));
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
         edProgrammer.setEnabled(isEditTask);
         edText.setEnabled(isEditTask);
         edPs.setEnabled(isEditTask);
         edDateCreated.setEnabled(false);
         edDateInWork.setEnabled(false);
         edDateClosed.setEnabled(false);
        tvIsInWork.setEnabled(isEditTask&&!isNewTask);
        tvIsClosed.setEnabled(isEditTask&&!isNewTask);

    }
    public void saveTaskToServer(){
        WUsers author = main_currentUser;
        WUsers programmer = null;
        WTask new_main_taskItem;
        if(edGuid.getText().toString()!="") {
            new_main_taskItem = new WTask(edGuid.getText().toString(),
                    edId.getText().toString(),
                    edName.getText().toString(),
                    author, programmer,
                    edText.getText().toString(),
                    edPs.getText().toString(),
                    tvIsClosed.isChecked(),
                    convert.getDateFromString(edDateClosed.getText().toString(), DATE_FORMAT_VID),
                    tvIsInWork.isChecked(),
                    convert.getDateFromString(edDateInWork.getText().toString(), DATE_FORMAT_VID),
                    convert.getDateFromString(edDateCreated.getText().toString(), DATE_FORMAT_VID),
                    false);
        }else{
            new_main_taskItem = new WTask(edName.getText().toString(),
                    author, programmer,
                    edText.getText().toString(),
                    edPs.getText().toString(),
                    tvIsInWork.isChecked(),
                    convert.getDateFromString(edDateInWork.getText().toString(), DATE_FORMAT_VID),
                    convert.getDateFromString(edDateCreated.getText().toString(), DATE_FORMAT_VID));

        }
        FormatData formatData = new FormatData();
        if(new_main_taskItem.getGuid().isEmpty()){//New task
            //Log.e("NEW TASK","NEWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
             InputStream is = getResources().openRawResource(R.raw.post_query_task);
            String ss1 = convert.getStringFromInputStream(is);
            String ss2 = formatData.format_task_post(ss1,new_main_taskItem);
            PostDataTask pdt = new PostDataTask(getActivity().getApplicationContext());
            pdt.execute(ss2,"Catalog_Tasks");

        }else{ //UPDATE task
           if(isModifiedTask) {
               //Log.e("UPATE TASK","UPDATEEEEEEEEEEEEEEEEEE");
               String ss = formatData.format_task_patch(getActivity().getApplicationContext(), new_main_taskItem);
               PatchDataTask patchDataTask = new PatchDataTask(getActivity().getApplicationContext());
               patchDataTask.execute(ss, "Catalog_Tasks", new_main_taskItem.getGuid(), "");
           }
        }
    }
}
