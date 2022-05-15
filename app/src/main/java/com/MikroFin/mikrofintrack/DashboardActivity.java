package com.MikroFin.mikrofintrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private static FirebaseUser firebaseUser;
    private FirebaseFirestore firestoredb;
    private static String uid;
    GoogleSignInClient gsc;
    FirebaseAuth mAuth;

    ImageView usermenuBtn;
    DrawerLayout dashboardDrawerLayout;
    NavigationView navigationView;
    TextView monthBtn,dbEarned,dbSpent,dbBalance;

    String _dbEarned ="__", _dbSpent= "__", _dbBalance= "__";

    Calendar calendar;



    String[]monthName={"January","February","March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

    //ActionBarDrawerToggle toggle;


    public static void startActivity(Context context, FirebaseUser user, String _uid) {
        Intent intent = new Intent(context, DashboardActivity.class);
        firebaseUser = user;
        uid = _uid;
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
        dbBalance = (TextView) findViewById(R.id.db_balance_txt);
        dbEarned = (TextView) findViewById(R.id.db_earned_txt);
        dbSpent = (TextView) findViewById(R.id.db_spent_txt);

        dbBalance.setText(_dbBalance);
        dbEarned.setText(_dbEarned);
        dbSpent.setText(_dbSpent);

        //initialize Firestore Database Handler
        firestoredb = FirebaseFirestore.getInstance();

        calendar = Calendar.getInstance();
        String month=monthName[calendar.get(Calendar.MONTH)];
        int year = calendar.get(Calendar.YEAR);

        String currMonth = month +" "+ String.valueOf(year);

        if (uid==null){
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user!=null){
                for (UserInfo profile : user.getProviderData()){
                    uid = profile.getUid();
                }
            } else {
                Toast.makeText(DashboardActivity.this, "Error! Please try signing in again! ", Toast.LENGTH_LONG).show();
            }
        }

        monthBtn.setText(currMonth);
        // Check if Current Month data exist if not add Current Month in Monts collection
        DocumentReference docRef = firestoredb
                .collection("Users")
                .document(uid)
                .collection("Months")
                .document(currMonth);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot docSnapshot = task.getResult();
                    if(!docSnapshot.exists()){
                        Map<String, Object> account = new HashMap<>();
                        account.put("Credit",0.0);
                        account.put("Debit",0.0);
                        account.put("Balance",0.0);
                        docRef.set(account);
                    }
                    else{
//                        Log.d(TAG, "DocumentSnapshot data: " + docSnapshot.getData());
                        Map<String,Object> account = new HashMap<>();
                        account = docSnapshot.getData();
                        if(account!=null){
                            Object _dbEarnedObj = account.get("Credit");
                            Object _dbSpentObj = account.get("Debit");
                            Object _dbBalanceObj = account.get("Balance");
                            _dbEarned = _dbEarnedObj.toString();
                            _dbSpent = _dbSpentObj.toString();
                            _dbBalance = _dbBalanceObj.toString();
                            dbBalance.setText(_dbBalance);
                            dbEarned.setText(_dbEarned);
                            dbSpent.setText(_dbSpent);
//                            Log.d(TAG, "DocumentSnapshot data earned: " + _dbEarned);
//                            Log.d(TAG, "DocumentSnapshot data spent: " + _dbSpent);
//                            Log.d(TAG, "DocumentSnapshot data balance: " + _dbBalance);
                        }
                    }
                }
            }
        });


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