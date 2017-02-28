package com.wolff.wolfffrest1c;
/*TAGS
#fragments
#fab - floating action button

 */


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.wolff.wolfffrest1c.fragments.Fragment_preference;
import com.wolff.wolfffrest1c.fragments.Fragment_task_item;
import com.wolff.wolfffrest1c.fragments.Fragment_task_list;
import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.rest.RESTInvoker;
import com.wolff.wolfffrest1c.tasks.GetDataTask;

import java.util.concurrent.ExecutionException;

import static com.wolff.wolfffrest1c.R.id.fab;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Fragment_task_list.FragmentTaskListListener {

    Fragment_task_list fragment_task_list;
    Fragment_preference fragment_preferences;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ПРОВЕРЯЕМ КОННЕКТ ЕСЛИ НЕТУ - ОТКРЫВАЕМ НАСТРОЙКИ

        //#fab
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
                //передаем данные в фрагмент
             Fragment_task_item fragment_task_item = Fragment_task_item.newInstance(null);
             displayFragment(fragment_task_item);

            }
        });

        //-#fab
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //#fragment
        fragment_preferences = new Fragment_preference();
        fragment_task_list = new Fragment_task_list();
        boolean isConnect = false;
            GetDataTask getDataTask1 =new GetDataTask(getApplicationContext());
        Log.e("TEST","1");
        try {
            String data1CSrv1 = getDataTask1.execute("Catalog_Пользователи/").get();
            if(data1CSrv1!=null) {
                Log.e("TEST","2 - "+data1CSrv1);
                isConnect = true;
            }else{
                Log.e("TEST","3");
                isConnect=false;
            }
        } catch (InterruptedException e) {
            Log.e("TEST","4");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("TEST","5");
            e.printStackTrace();
        }
          if(isConnect){
            displayFragment(fragment_task_list);
        }else {
            displayFragment(fragment_preferences);

        }


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tasks) {
            displayFragment(fragment_task_list);
         } else if (id == R.id.nav_settings) {
            displayFragment(fragment_preferences);

        } else if (id == R.id.nav_exit) {

        } else if (id == R.id.nav_about) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //#fragment
    private void displayFragment(Fragment fragment){
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

    @Override
    public void onTaskSelected(WTask task) {
        Fragment_task_item fragment_task_item = Fragment_task_item.newInstance(task);
        displayFragment(fragment_task_item);    }
}
