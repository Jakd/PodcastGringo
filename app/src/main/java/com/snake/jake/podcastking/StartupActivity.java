package com.snake.jake.podcastking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        Button tempButton = (Button) findViewById(R.id.btn_start);
        tempButton.setOnClickListener(view -> startActivity(new Intent(StartupActivity.this, PlayerActivity.class)));
    }
}
