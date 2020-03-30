package com.example.spendingtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddExpense extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference reff;
    ArrayList<String> friendListArray=new ArrayList<String>();
    Button btnPaid;
    Button btnPayer;

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        btnPaid=findViewById(R.id.btnPaid);
        btnPayer=findViewById(R.id.btnPayer);
        btnPaid.setOnClickListener(AddExpense.this);
        btnPayer.setOnClickListener(AddExpense.this);
        SharedPreferences sp = getSharedPreferences("uName", MODE_PRIVATE);
        String gName=sp.getString("uname", "Not Found");
        reff= FirebaseDatabase.getInstance().getReference().child("Users").child(gName).child("friends");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value=dataSnapshot.getKey();
                friendListArray.add(value);
                checkedItems = new boolean[friendListArray.size()];
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



    public void showAlert(){
        listItems=new String[friendListArray.size()];

        listItems = friendListArray.toArray(listItems);

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(AddExpense.this);
        mBuilder.setTitle("Paid Between");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if(isChecked){
                    if(!mUserItems.contains(position)){
                        checkedItems[position]=true;
                        mUserItems.add(position);
                    }
                }else{
                    mUserItems.remove((Integer.valueOf(position)));
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < mUserItems.size(); i++) {
                    item = item + listItems[mUserItems.get(i)];
                    if (i != mUserItems.size() - 1) {
                        item = item + ", ";
                    }
                }

                Toast.makeText(AddExpense.this, ""+item, Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog=mBuilder.create();
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPaid:
                showAlert();
                break;
            case R.id.btnPayer:
                showPayer();
                break;
        }

    }

    public void showPayer() {
        AlertDialog.Builder alertPayerBuilder=new AlertDialog.Builder(AddExpense.this);
        listItems=new String[friendListArray.size()];
        listItems = friendListArray.toArray(listItems);
        final CharSequence[] charSequences=listItems;
        alertPayerBuilder.setTitle("Choose Payer");
        alertPayerBuilder.setItems(charSequences, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnPayer.setText(charSequences[which]);
                Toast.makeText(AddExpense.this, ""+charSequences[which], Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog dialog=alertPayerBuilder.create();
        dialog.show();
    }
}
