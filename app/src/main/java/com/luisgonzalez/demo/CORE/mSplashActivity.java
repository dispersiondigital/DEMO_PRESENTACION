package com.luisgonzalez.demo.CORE;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.dispersiondigital.smartclaritydemo.R;

public class mSplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_splash);
        progressBar = (ProgressBar) findViewById(R.id.progressbar1);

        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus = progressBarStatus +1;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {

                    Intent mainIntent = new Intent().setClass(
                            mSplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    //overridePendingTransition(R.anim.zoom_forward_out,R.anim.zoom_forward_out);
                    finish();
                    // close the progress bar dialog
                }
            }
        }).start();
    }
}
