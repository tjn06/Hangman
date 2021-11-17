package com.example.svghangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Result extends AppCompatActivity implements View.OnClickListener{

    private ImageView infoBtn;
    private ImageView playBtn;
    Button backToMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Using toolbar as ActionBar
        setSupportActionBar(toolbar);
        //Display application icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_small_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

        //Buttons in actionbar
        infoBtn = (ImageView) findViewById(R.id.info);
        infoBtn.setOnClickListener(this);
        playBtn = (ImageView) findViewById(R.id.play);
        playBtn.setOnClickListener(this);

        //Buttons
        backToMainMenu = (Button) findViewById(R.id.backToMainMenu);
        backToMainMenu.setOnClickListener(this);

        //Get result from GameActivity, update ui with putextras-data
        Intent intent = getIntent();
        HashMap<String, String> results = (HashMap<String, String>) intent.getSerializableExtra("results");

        TextView resultMess = (TextView) findViewById(R.id.resultMess);
        if (results.get("winOrLose").equals("You won!")) {
            resultMess.setTextColor(Color.parseColor("#538642"));
        } else {
            resultMess.setTextColor(Color.parseColor("#B33131"));
        }
        resultMess.setText(results.get("winOrLose"));

        TextView theWord = (TextView) findViewById(R.id.theWord);
        theWord.setText(results.get("word"));

        TextView tries = (TextView) findViewById(R.id.tries);
        tries.setText(results.get("tries"));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.play:
                goToGame();
                break;
            case R.id.info:
                goToInformation();
                break;
            case R.id.backToMainMenu:
                goToMainActivity();
                break;
        }
    }

    public void goToGame (){
        Intent goToGameIntent = new Intent(this, Game.class); //Create intent
        startActivity(goToGameIntent); //Start next activity(LoginActivity)
    }
    public void goToInformation (){
        Intent goToInformationIntent = new Intent(this, Information.class); //Create intent
        startActivity(goToInformationIntent); //Start next activity(LoginActivity)
    }

    private void goToMainActivity() {
        Intent backToMainMenuIntend = new Intent(this, MainActivity.class);
        startActivity(backToMainMenuIntend);
    }
}