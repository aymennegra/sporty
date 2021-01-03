package com.example.sportybetafinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MessageActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);


        //EditText editText = findViewById(R.id.editText);
        sharedPreferences = getApplicationContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String idu = sharedPreferences.getString("nomUser","" );
        findViewById(R.id.enterBtn)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("name", idu);
                    startActivity(intent);
                    Toast.makeText(MessageActivity.this, idu, Toast.LENGTH_SHORT).show();

                });

    }
}
