package com.MikroFin.mikrofintrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;


public class DashboardActivity extends AppCompatActivity {
    private static FirebaseUser firebaseUser;
    GoogleSignInClient gsc;
    FirebaseAuth mAuth;
    DrawerLayout dashboardDrawerLayout;
    NavigationView navigationView;
    Calendar calendar;
    TextView monthBtn;
    ImageView usermenuBtn;

    String[]monthName={"January","February","March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

    //ActionBarDrawerToggle toggle;


    public static void startActivity(Context context, FirebaseUser user, String uid) {
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
        monthBtn = (TextView) findViewById(R.id.monthbtn);
        usermenuBtn = (ImageView) findViewById(R.id.DBUserButton);

        calendar = Calendar.getInstance();
        String month=monthName[calendar.get(Calendar.MONTH)];
        int year = calendar.get(Calendar.YEAR);

        String currMonth = month +" "+ String.valueOf(year);

        monthBtn.setText(currMonth);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mAuth = FirebaseAuth.getInstance();
        gsc= GoogleSignIn.getClient(DashboardActivity.this, gso);


        usermenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideRight();
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
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mAuth.signOut();
                Toast.makeText(DashboardActivity.this, "Logging Out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DashboardActivity.this,FirstPage.class));
            }
        });


    }

    private void slideRight() {
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