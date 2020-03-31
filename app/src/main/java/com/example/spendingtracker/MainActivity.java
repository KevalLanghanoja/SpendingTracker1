package com.example.spendingtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spendingtracker.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    EditText etLEmail,etLPassword;
    Button btnLLogin;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        String name = sp.getString("uname", "Not");
        if(name.equals("Not")) {


            database = FirebaseDatabase.getInstance();
            users = database.getReference("Users");

            textView = findViewById(R.id.tvLSignup);
            etLEmail = findViewById(R.id.etLEmail);
            etLPassword = findViewById(R.id.etLPassword);
            btnLLogin = findViewById(R.id.btnLLogin);
            textView.setOnClickListener(MainActivity.this);
            btnLLogin.setOnClickListener(MainActivity.this);
        }
        else {
            startActivity(new Intent(MainActivity.this,Home.class));
            MainActivity.this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLSignup:
                startActivity(new Intent(MainActivity.this, Registration.class));
                MainActivity.this.finish();
                break;
            case R.id.btnLLogin:
                login(etLEmail.getText().toString(),etLPassword.getText().toString());
                break;
        }
    }

    private void login(final String username, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists())
                {
                    if (!username.isEmpty())
                    {
                        User login=dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword().equals(password)) {
                            String Email = login.getMobile();
                            String Username=login.getUsername();
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                            SharedPreferences.Editor spedit = sp.edit();
                            spedit.putString("uname",Username);
                            spedit.putString("email",Email);
                            spedit.commit();
                            startActivity(new Intent(MainActivity.this,Home.class));
                            MainActivity.this.finish();

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Fill All Field", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "User is not Registerd", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

