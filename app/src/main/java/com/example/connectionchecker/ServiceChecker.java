package com.example.connectionchecker;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static android.widget.Toast.makeText;

public class ServiceChecker extends Service{

    public static int code =0;
    private CountDownTimer timer;
    private long timeMilliseconds;
    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    private void connect(Intent intent)
    {
        String urlS= "";
        if(intent.hasExtra("urlStr"))
        {
            urlS = intent.getStringExtra("urlStr");
        }
        else System.out.println("Problem");
        try {
            URL url = new URL(urlS);
            URLConnection urlConnection = (URLConnection) url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlConnection;

            code = connection.getResponseCode();

            Intent intent1 = new Intent();
            intent1.setAction("lab.stepan.receiver");
            intent1.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent1.putExtra("codeStatus",code);
            intent1.putExtra("urlRes",urlS);
            sendBroadcast(intent1);


        } catch (Exception e) {
            e.getMessage();
        }
    }


    public int onStartCommand(Intent intent, int flags,int startId)
    {
        timeMilliseconds = 5000;
        timer = new CountDownTimer(timeMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMilliseconds = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connect(intent);
                    }
                });
                thread.start();
            }
        }.start();

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent)
    {
        super.onRebind(intent);
    }

    public void onDestroy()
    {
        super.onDestroy();
    }


}
