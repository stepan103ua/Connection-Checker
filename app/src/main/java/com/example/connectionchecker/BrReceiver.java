package com.example.connectionchecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;


public class BrReceiver extends BroadcastReceiver {

    private CountDownTimer timer;
    private long timeMilliseconds;
    @Override
    public void onReceive(Context context, Intent intent) {
        timeMilliseconds = 5000;
        if (!intent.hasExtra("codeStatus")) {
            Intent intent1 = new Intent(context, ServiceChecker.class);
            if (intent.hasExtra("urlRes")) {
                String urlName = intent.getStringExtra("urlRes");
                urlName = urlName.substring(8, urlName.length());
                urlName = String.format(urlName).replace('/', '|');
                System.out.println(intent.getStringExtra("urlRes"));
                intent1.putExtra("urlStr", intent.getStringExtra("urlRes"));
                context.startService(intent1);
            }
        }
        else
            {
            int code = 404;
            if (intent.hasExtra("codeStatus")) {
                code = intent.getIntExtra("codeStatus", 0);
                Intent intent1 = new Intent(context, ServiceChecker.class);
                if (intent.hasExtra("urlRes")) {
                    String urlName = intent.getStringExtra("urlRes");
                    urlName = urlName.substring(8, urlName.length());
                    urlName = String.format(urlName).replace('/', '|');
                    DBManager db = new DBManager(context, "my_table", urlName + ".db", "test");
                    db.openDB();
                    if (code == 200)
                        db.addToDB("Success");
                    else db.addToDB("Failure");
                    db.closeDb();

                    System.out.println(intent.getStringExtra("urlRes"));
                    intent1.putExtra("urlStr", intent.getStringExtra("urlRes"));
                    context.startService(intent1);
                }
            }
        }
    }
}