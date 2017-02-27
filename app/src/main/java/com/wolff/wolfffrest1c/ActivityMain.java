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

import com.wolff.wolfffrest1c.fragments.Fragment_task_item;
import com.wolff.wolfffrest1c.fragments.Fragment_task_list;
import com.wolff.wolfffrest1c.objects.WTask;

import static com.wolff.wolfffrest1c.R.id.fab;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Fragment_task_list.FragmentTaskListListener {

    Fragment_task_list fragment_task_list;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        fragment_task_list = new Fragment_task_list();
        displayFragment(fragment_task_list);

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
