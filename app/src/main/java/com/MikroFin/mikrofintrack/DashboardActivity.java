package com.MikroFin.mikrofintrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import javax.annotation.meta.When;


public class DashboardActivity extends AppCompatActivity {
    private static FirebaseUser firebaseUser;
    DrawerLayout dashboardDrawerLayout;
    NavigationView navigationView;
    //ActionBarDrawerToggle toggle;


    public static void startActivity(Context context, FirebaseUser user) {
        Intent intent = new Intent(context, DashboardActivity.class);
        firebaseUser = user;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardDrawerLayout = (DrawerLayout) findViewById(R.id.dashboardDrawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navView);


        ImageView imageView = (ImageView) findViewById(R.id.DBUserButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLeft();
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.miItem1:{
                        Toast.makeText(DashboardActivity.this, "item1", Toast.LENGTH_LONG).show();
                    }
                    case R.id.miItem2:{
                        Toast.makeText(DashboardActivity.this, "item2", Toast.LENGTH_LONG).show();
                    }
                    case R.id.dblogout:{
                        dblogout();
                    }
                }
                return true;
            }
        });
//        toggle = new ActionBarDrawerToggle(this,dashboardDrawerLayout,R.string.open,R.string.close);
//        dashboardDrawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setHomeAsUpIndicator(R.drawable.user);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                return false;
//            }
//        });

    }

    private void dblogout() {
        //firebaseUser.delete();
        
        Toast.makeText(DashboardActivity.this, "Logging Out", Toast.LENGTH_LONG).show();
        startActivity(new Intent(DashboardActivity.this,FirstPage.class));
    }

    private void slideLeft() {
        dashboardDrawerLayout.openDrawer(navigationView);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (toggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}