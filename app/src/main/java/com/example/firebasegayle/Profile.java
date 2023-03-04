package com.example.firebasegayle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    private Button buttonLogout, buttonAccounting, buttonCashier;
    TextView textStatus;
    private volatile boolean stopThreadFlag = false;

    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonAccounting = findViewById(R.id.buttonAccounting);
        buttonCashier = findViewById(R.id.buttonCashier);
        textStatus = findViewById(R.id.textStatus);

        buttonAccounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              buttonAccounting(10);
            }
        });

        buttonCashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopThread();
                textStatus.setText("STATUS");
                Toast.makeText(Profile.this, "Proceeding to Cashier"
                        , Toast.LENGTH_SHORT).show();
            }
        });

        buttonLogout = findViewById(R.id.buttonLogout);
            buttonLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishAffinity();
                    System.exit(0);
                }
            });
        }

    public void buttonAccounting(int seconds) {
        AnahawanRunnable runnable = new AnahawanRunnable(5);
        new Thread(runnable).start();
    }

    public void stopThread() {
        stopThreadFlag = true;
    }

    class AnahawanRunnable implements Runnable {
        int seconds;

        //Non-Default Constructor
        AnahawanRunnable(int seconds){
            this.seconds = seconds;
        }
        @Override
        public void run() {
            for(int i =0; i< seconds; i++){
                if(stopThreadFlag)
                {
                    return;
                }
                if(i == 3) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            textStatus.setText("Proceed to Cashier");
                        }
                    });

                }
                Log.d("THREAD ACTIVITY", "Start Thread : " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}