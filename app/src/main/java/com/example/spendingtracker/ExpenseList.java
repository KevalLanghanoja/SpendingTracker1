package com.example.spendingtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ExpenseList extends Fragment implements View.OnClickListener {

    ListView expenseList;
    DatabaseReference reff;
    ArrayList<String> expenseListArray = new ArrayList<>();
    ArrayList<String> expenseListDescription = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_expense_list, container, false);
        expenseList = v.findViewById(R.id.expenseList);

        FloatingActionButton floatingActionButton=v.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(ExpenseList.this);

        SharedPreferences sp = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String gName = sp.getString("uname", "Not Found");

        final CustomAdapter1 customAdapter1=new CustomAdapter1();
        expenseList.setAdapter(customAdapter1);

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(gName).child("Expense");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               expenseListArray.add(dataSnapshot.child("amount").getValue(String.class));
               expenseListDescription.add(dataSnapshot.child("description").getValue(String.class));
                customAdapter1.notifyDataSetChanged();
            }

            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                expenseListArray.add(dataSnapshot.child("amount").getValue(String.class));
                expenseListDescription.add(dataSnapshot.child("description").getValue(String.class));
            }
            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Inflate the layout for this fragment
        return v;
    }
    class CustomAdapter1 extends BaseAdapter{

        @Override
        public int getCount() {
            return expenseListArray.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.customexpenselist,null);
            TextView tvAmount=(TextView) convertView.findViewById(R.id.tvAmount);
            TextView tvDescription=(TextView) convertView.findViewById(R.id.tvDescription);
            tvDescription.setText(expenseListDescription.get(position));
            tvAmount.setText(expenseListArray.get(position));
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(),AddExpense.class));
        Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

    }
}