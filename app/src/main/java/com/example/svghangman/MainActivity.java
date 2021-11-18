package com.example.svghangman;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView infoBtn;
    private ImageView playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Using toolbar as ActionBar
        setSupportActionBar(toolbar);
        //Display application icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_small_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

        infoBtn = (ImageView) findViewById(R.id.info);
        infoBtn.setOnClickListener(this);

        playBtn = (ImageView) findViewById(R.id.play);
        playBtn.setOnClickListener(this);

        Button goToGameBtn = (Button) findViewById(R.id.goToGameBtn);
        goToGameBtn.setOnClickListener(this);

        Button goToInformationBtn = (Button) findViewById(R.id.goToInformationBtn);
        goToInformationBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.goToGameBtn:
            case R.id.play:
                goToGame();
                break;
            case R.id.goToInformationBtn:
            case R.id.info:
                goToInformation();
                break;
        }
    }

    public void goToGame (){
        Intent goToGameIntent = new Intent(this, GameActivity.class); //Create intent
        startActivity(goToGameIntent); //Start next activity(LoginActivity)
    }
    public void goToInformation (){
        Intent goToInformationIntent = new Intent(this, InformationActivity.class); //Create intent
        startActivity(goToInformationIntent); //Start next activity(LoginActivity)
    }

}