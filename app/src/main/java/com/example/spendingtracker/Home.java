package com.example.spendingtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    TextView textView;
    TextView tEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//      For set a user Detail
        textView = findViewById(R.id.tUserName);
        tEmail = findViewById(R.id.tEmail);
        SharedPreferences sp = getSharedPreferences("uName", MODE_PRIVATE);
        textView.setText("Welcome " + sp.getString("uname", "Not Found"));
        tEmail.setText("" + sp.getString("email", "Not Found"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//      Toogle Button on Top off the Screen
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Home.this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
//Create a Toogle Button Onclick Event and Manage Close Button
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_message:
                Toast.makeText(this, "Add Friends", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),addFriend.class));
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("uName", MODE_PRIVATE);
                SharedPreferences.Editor spedit = sp.edit();
                spedit.clear();
                spedit.commit();
                Home.this.finish();

                startActivity(new Intent(Home.this,MainActivity.class));

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
