package com.example.connectionchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddingSIteActivity extends AppCompatActivity {

    private TextView urlText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_site);
        urlText = findViewById(R.id.urlText);
    }

    public void OnAddClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("urlString",urlText.getText().toString());
        setResult(1,intent);
        finish();
    }
}