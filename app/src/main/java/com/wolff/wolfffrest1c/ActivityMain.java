package com.wolff.wolfffrest1c;
/*TAGS
#fragments
#fab - floating action button

 */


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wolff.wolfffrest1c.fragments.Fragment_preference;
import com.wolff.wolfffrest1c.fragments.Fragment_task_item;
import com.wolff.wolfffrest1c.fragments.Fragment_task_list;
import com.wolff.wolfffrest1c.jSon.JsonParser;
import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.objects.WUsers;
import com.wolff.wolfffrest1c.tasks.GetDataTask;
import com.wolff.wolfffrest1c.tools.FormatData;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Fragment_task_list.FragmentTaskListListener {

    Fragment_task_list fragment_task_list;
    Fragment_preference fragment_preferences;
    FloatingActionButton fab;
    ArrayList<WUsers> main_userList;
    WUsers main_author;
    SharedPreferences prefer;
    boolean isConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast toast;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //#fab
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //передаем данные в фрагмент
             Fragment_task_item fragment_task_item = Fragment_task_item.newInstance(null,main_author,main_userList);
             displayFragment(fragment_task_item);

            }
        });

        //-#fab
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(ActivityMain.this, DividerItemDecoration.HORIZONTAL));
        //navigationView.getMenu().
        //#fragment
        fragment_preferences = new Fragment_preference();
        fragment_task_list = new Fragment_task_list();

        //ПРОВЕРЯЕМ КОННЕКТ ЕСЛИ НЕТУ - ОТКРЫВАЕМ НАСТРОЙКИ
        isConnect = connectAndGetUsers();
        //HEADER
        View headerLayout = navigationView.getHeaderView(0);
        TextView tvHeader_BaseName = (TextView) headerLayout.findViewById(R.id.tvHeader_baseName);
        TextView tvHeader_UserName = (TextView) headerLayout.findViewById(R.id.tvHeader_UserName);
        tvHeader_BaseName.setText("Название 1С базы: "+prefer.getString("baseName",""));
        tvHeader_UserName.setText("Имя пользователя: "+prefer.getString("serverLogin",""));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
             case R.id.action_undo:
                displayFragment(fragment_task_list);
                break;
            case R.id.action_save:
                //Log.e("MENU ACTIV","SAVE");
                displayFragment(fragment_task_list);
            default:
               // Log.e("MENU FRAGM","DEFAULT MAIN");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tasks) {
            if(!isConnect){
                isConnect=connectAndGetUsers();
            }else {
                displayFragment(fragment_task_list);
            }
         } else if (id == R.id.nav_settings) {
            displayFragment(fragment_preferences);
        } else if (id == R.id.nav_exit) {
            System.exit(0);
        } else if (id == R.id.nav_about) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //#fragment
    public void displayFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_activity_main,fragment);
        fragmentTransaction.commit();
        if(fragment.getClass().getSimpleName().equalsIgnoreCase("Fragment_task_list")){
            fab.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.INVISIBLE);
        }
    }

    public boolean connectAndGetUsers(){
        boolean isConnect = false;
        Toast toast;
        prefer =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(prefer.getString("serverLogin",null)!=null&&prefer.getString("baseName",null)!=null) {
            GetDataTask getDataTask1 =new GetDataTask(getApplicationContext());
            String data1CSrv1 = null;
            try {
                data1CSrv1 = getDataTask1.execute("Catalog_Пользователи/", "").get();
                JsonParser parser = new JsonParser();
                if(data1CSrv1!=null) {
                    main_userList = parser.getUserListFromServerData(data1CSrv1);
                    FormatData formatData = new FormatData();
                    main_author = formatData.getCurrentUserByUid(main_userList, prefer.getString("serverLogin", ""));
                    isConnect = true;
                }
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }else{

        }
        if(isConnect){
            toast = Toast.makeText(getApplicationContext(),"Подключение к серверу успешно",Toast.LENGTH_LONG);
            toast.show();
            Bundle args = new Bundle();
            args.putSerializable("UserList",main_userList);
            args.putSerializable("Author",main_author);
            fragment_task_list.setArguments(args);
            displayFragment(fragment_task_list);
        }else {
            toast = Toast.makeText(getApplicationContext(),"Не удалось подключиться к серверу. Проверьте настройки",Toast.LENGTH_LONG);
            toast.show();
            displayFragment(fragment_preferences);
        }
        return isConnect;
    }
    @Override
    public void onTaskSelected(WTask task,WUsers author) {
        Fragment_task_item fragment_task_item = Fragment_task_item.newInstance(task,author,main_userList);
        displayFragment(fragment_task_item);    }
}
