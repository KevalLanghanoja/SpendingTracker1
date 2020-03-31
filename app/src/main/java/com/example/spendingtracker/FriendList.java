package com.example.spendingtracker;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

public class FriendList extends Fragment {
    ListView friendList;
    DatabaseReference reff;
    ArrayList<String> friendListArray = new ArrayList<String>();

    public FriendList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friend_list, container, false);
        friendList = v.findViewById(R.id.friendList);


        View empty =  v.findViewById(R.id.empty);
        SharedPreferences sp = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String gName = sp.getString("uname", "Not Found");
        // Inflate the layout for this fragment

        final CustomAdpter customAdpter = new CustomAdpter();
        friendList.setAdapter(customAdpter);
        friendList.setEmptyView(empty);


        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(gName).child("friends");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                friendListArray.add(value);
                customAdpter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                friendListArray.add(value);
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
        return v;
    }


    class CustomAdpter extends BaseAdapter {

        @Override
        public int getCount() {
            return friendListArray.size();
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
            convertView = getLayoutInflater().inflate(R.layout.customlistview, null);
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView friendName = convertView.findViewById(R.id.friendName);
            friendName.setText(friendListArray.get(position));
            return convertView;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
