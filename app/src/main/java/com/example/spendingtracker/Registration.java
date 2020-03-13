package com.example.spendingtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Registration extends AppCompatActivity implements View.OnClickListener {

    TextView tvRLogin;
    EditText etRUsername, etRPassword, etRMobile, etRConfirmPassword;
    Button btnRSignup;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        tvRLogin = findViewById(R.id.tvRLogin);
        etRUsername = findViewById(R.id.etRUsername);
        etRMobile = findViewById(R.id.etRMobile);
        etRPassword = findViewById(R.id.etRPassword);
        etRConfirmPassword = findViewById(R.id.etRConfirmPassword);
        btnRSignup = findViewById(R.id.btnRSignup);
        tvRLogin.setOnClickListener(Registration.this);
        btnRSignup.setOnClickListener(Registration.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRLogin:
                startActivity(new Intent(Registration.this, MainActivity.class));
                Registration.this.finish();
                break;
            case R.id.btnRSignup:

               if(etRUsername.getText().toString().isEmpty() || etRPassword.getText().toString().length()<6 || etRMobile.getText().toString().length()<5)
                {
                    if(etRUsername.getText().toString().length()<5)
                    {
                        Toast.makeText(this, "Username must me 5 letter", Toast.LENGTH_SHORT).show();
                    }
                    else if(etRMobile.getText().toString().length() <10){
                        Toast.makeText(this, "Enter valid Mobile No", Toast.LENGTH_SHORT).show();
                    }
                    else if(etRPassword.getText().toString().length()<6) {
                        Toast.makeText(this, "password length is minimum 6 Digit", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(etRPassword.getText().toString().equals(etRConfirmPassword.getText().toString())) {

                    final User user= new User(etRUsername.getText().toString(),etRPassword.getText().toString(),etRMobile.getText().toString());
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(user.getUsername()).exists()) {
                                Toast.makeText(Registration.this, "Group Name is already Exist", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                users.child(user.getUsername()).setValue(user);
                                Toast.makeText(Registration.this, "Successfully Register", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this,MainActivity.class));
                                Registration.this.finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                }
                    else {
                        Toast.makeText(this, "Password and Confirm Password is not Match", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

        }
    }
}
