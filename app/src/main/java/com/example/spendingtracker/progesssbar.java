package com.example.spendingtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class progesssbar extends AppCompatActivity {
    ProgressBar mProgressBar;
    TextView mLoadingText;
    int mProgressStatus = 0;
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progesssbar);



            mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
            mLoadingText = (TextView) findViewById(R.id.LoadingCompleteTextView);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mProgressStatus < 100){
                        mProgressStatus++;
                        SystemClock.sleep(10);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(mProgressStatus);
                            }
                        });
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(isNetworkConnected()){
                                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                                String name = sp.getString("uname", "Not");
                                if(name.equals("Not")) {
                                    startActivity(new Intent(progesssbar.this,MainActivity.class));
                                    progesssbar.this.finish();

                                }
                                else {
                                    startActivity(new Intent(progesssbar.this,Home.class));
                                    progesssbar.this.finish();
                                }    
                            }
                            else {
                                startActivity(getIntent());
                                finish();

                                Toast.makeText(progesssbar.this, "Please check Interner Connection", Toast.LENGTH_SHORT).show();
                            }
                            
                        }
                    });
                }
            }).start();
        }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
