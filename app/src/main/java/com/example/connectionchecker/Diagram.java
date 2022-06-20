package com.example.connectionchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Vector;

public class Diagram extends AppCompatActivity{
    private DBManager db;
    private Intent intent;
    private String url;
    private Vector<String> list;
    private ListView lw;
    private ProgressBar diagram;
    private Button delBut;
    private TextView percText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);
        intent = getIntent();
        delBut = findViewById(R.id.button);
        percText = findViewById(R.id.percentText);
        if(intent.hasExtra("url"))
        {
            url = intent.getStringExtra("url");
            url = url.substring(8, url.length());
            url = String.format(url).replace('/','|');
            db = new DBManager(this, "my_table", url + ".db", "test");
            db.openDB();
            list = db.getDB();
            lw = findViewById(R.id.listView);
            this.addToListView();
        }else System.out.println("fail");
        diagram = findViewById(R.id.diagram);
        setDiagram();
    }
    protected void onResume() {
        super.onResume();
    }

    private void addToListView()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view_layout,R.id.textView,db.getDB());
        lw.setAdapter(adapter);
    }

    private void setDiagram()
    {
        double success=0,fail=0,pr;
        for(String i : db.getDB())
        {
            if(i.equals("Success"))success++;
            else fail++;
        }
        pr = (success / (success + fail) * 100);
        System.out.println(pr);
        diagram.setProgress((int)pr);
        percText.setText(Integer.toString((int)pr) +"%");
    }

    protected void onDestroy() {

        super.onDestroy();
        db.closeDb();
    }

    public void onDeleteClick(View view) {
        db.removeDb();
        setDiagram();
        addToListView();
    }
}