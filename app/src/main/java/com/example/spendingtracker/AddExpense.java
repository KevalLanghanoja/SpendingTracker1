package com.example.spendingtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddExpense extends AppCompatActivity implements View.OnClickListener {
    ListView friendList;
    AlertDialog myDialog;
    DatabaseReference reff;
    ArrayList<String> friendListArray=new ArrayList<String>();
    Spinner spinner;
    TextView tvPaid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        tvPaid=findViewById(R.id.tvPaid);
        tvPaid.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("uName", MODE_PRIVATE);
        String gName=sp.getString("uname", "Not Found");
        reff= FirebaseDatabase.getInstance().getReference().child("Users").child(gName).child("friends");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value=dataSnapshot.getKey();
                friendListArray.add(value);
                spinner=findViewById(R.id.spinner1);
                ArrayAdapter <String> myAdapter=new ArrayAdapter<String>(AddExpense.this,android.R.layout.simple_list_item_1,friendListArray);
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    String[] mStringArray = new String[friendListArray.size()];

    public void showAlert(){
        AlertDialog.Builder myBilder=new AlertDialog.Builder(AddExpense.this);
        final ArrayList<String> selectedItems=new ArrayList();
        mStringArray = friendListArray.toArray(mStringArray);
        myBilder.setTitle("Paid Betweeen").setMultiChoiceItems(mStringArray,null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if(isChecked){
                    if(!selectedItems.contains(position)){
                        selectedItems.add(mStringArray[position]);
                    }
                }
                else {
                    selectedItems.remove(mStringArray[position]);
                }
            }
        });
        myBilder.setPositiveButton("Selected", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder sb=new StringBuilder();
                for (Object i:selectedItems){
                    sb.append(i.toString()+"\n");
                }
                Toast.makeText(AddExpense.this, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        myDialog=myBilder.create();
        myDialog.show();
    }

    @Override
    public void onClick(View v) {
        showAlert();
    }
}
