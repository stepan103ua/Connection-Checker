package com.example.connectionchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private Button connectButton,showButton,conBut;
    private int code;
    private ListView lw;
    public BrReceiver receiver;
    private Vector<String> list;
    private IntentFilter intentFilter;
    private DBManager db;
    private boolean conStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViews();
        intentFilter = new IntentFilter("lab.stepan.receiver");
        receiver = new BrReceiver();
        list = new Vector<>();
        db = new DBManager(this,"my_table","sites.db","title");


        lw = findViewById(R.id.listViewId);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id)
            {
                Intent diagramIntent = new Intent(getApplicationContext(),Diagram.class);
                diagramIntent.putExtra("url",list.get(position));
                startActivity(diagramIntent);
            }
        }
        );
    }


    protected void onResume()
    {
        super.onResume();
        db.openDB();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view_layout,R.id.textView,db.getDB());
        lw.setAdapter(adapter);
        list = db.getDB();
    }


    private void initViews()
    {
        connectButton = (Button) findViewById(R.id.buttonAdd);
        showButton = (Button) findViewById(R.id.deleteButton);
        conBut = (Button) findViewById(R.id.conButton);
    }

    public void connect(String url)
    {
        System.out.println("Started");
        Intent intentS = new Intent();
        intentS.setAction("lab.stepan.receiver");
        intentS.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intentS.putExtra("urlRes",url);
        sendBroadcast(intentS);


    }
    public void onConnectClick(View view) {
        Intent intents = new Intent(this,AddingSIteActivity.class);
        startActivityForResult(intents,1);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null)
        {
            return;
        }
        String url = data.getStringExtra("urlString");
        System.out.println(url);
        db.addToDB(url);
        list.clear();
        for(String i : db.getDB())
        {
            addUrl(i);
        }
    }


    private void addUrl(String url)
    {
        list.add(url);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view_layout,R.id.textView,list);
        lw.setAdapter(adapter);
    }


    public void onShowClick(View view) {

        db.removeDb();
        list.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view_layout,R.id.textView,list);
        lw.setAdapter(adapter);
        /*System.out.println("Stopped");
        unregisterReceiver(receiver);
        stopService(new Intent(this,ServiceChecker.class));*/
    }

    public void getData(int code)
    {
    }
    protected void onDestroy() {

        super.onDestroy();
        db.closeDb();
    }


    public void onConnectionClick(View view) {
        if(!this.conStarted)
        {
            conBut.setText("Stop connecting");
            conBut.setBackgroundColor(Color.rgb(255,0,0));
            this.conStarted = true;
        }
        else
        {
            this.conStarted = false;
            conBut.setText("Start connecting");
            conBut.setBackgroundColor(Color.rgb(76,175,80));
        }

        if(this.conStarted)
        {
            registerReceiver(receiver,intentFilter);
            list = db.getDB();
            for(String i : list)
            {
                connect(i);
            }
        }
        else
        {
            unregisterReceiver(receiver);
        }

    }

    public void onDiagramButtonClick(View view) {
        Intent intent = new Intent(this, DiagramCanvas.class);
        int noConnection =0 , connected=0;
        for(String i : list)
        {
            String url = i;
            url = url.substring(8,url.length());
            url = String.format(url).replace('/','|');
            DBManager d = new DBManager(this,"my_table",url + ".db","test");
            d.openDB();
            Vector<String> a = d.getDB();
            d.closeDb();
            for(String j : a)
            {
                if(j.equals("Success"))connected++;
                else noConnection++;
            }
        }
        float res = ((float)connected / ((float)connected + (float)noConnection));
        System.out.println(res);
        intent.putExtra("pr",res);
        startActivity(intent);
    }
}