/*
An Android App that helps EMT´s in Austria with VirtEBA.
Also an age calculator
Copyright: Lukas Becker, 2019
*/


package com.lukasbeckercode.virtebahelper;

//imports handled by IntelliJ
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

/**
 * StartUpActivity
 * <summary>The main screen that is shown on startup</summary>*/

public class StartUpActivity extends AppCompatActivity {
    public static int kat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        //Create the Buttons
        //Button Variables
        Button openSearchBtn = findViewById(R.id.openSearchBtn);
        Button openChirBtn = findViewById(R.id.openChirBtn); // 1
        Button openInternBtn = findViewById(R.id.openMedBtn); //2
        Button openNeuroBtn = findViewById(R.id.openNeuroBtn); //3
        Button openPsychBtn = findViewById(R.id.openPsychBtn); //4
        Button openGynBtn = findViewById(R.id.openGynBtn); //5
        Button openSonstBtn = findViewById(R.id.openMiscBtn);//6
        Button openAgeCalcBtn = findViewById(R.id.openAgeCalcBtn);
        Button openTypeAndCodeBtn = findViewById(R.id.openTypeAndCodeBtn);

        InputStream stream = this.getResources().openRawResource(R.raw.pzc);
        CodeHandler handler = new CodeHandler(stream);
        handler.readData(); //call the Method to read the Text-file (argument is the location of the file)

        //Assign the Buttons
        openSearchBtn.setOnClickListener((v)->openSearchActivity());
        openChirBtn.setOnClickListener(v->{
            openCategoryViewActivity();
            kat = 1;
        });
        openInternBtn.setOnClickListener(v->{
           openCategoryViewActivity();
            kat = 2;
        });
        openNeuroBtn.setOnClickListener(v->{
           openCategoryViewActivity();
            kat = 3;
        });
        openPsychBtn.setOnClickListener(v->{
           openCategoryViewActivity();
            kat = 7;
        });
        openGynBtn.setOnClickListener(v->{
            openCategoryViewActivity();
            kat = 4;
        });
        openSonstBtn.setOnClickListener(v->{
            openCategoryViewActivity();
            kat = 6;
        });
        openAgeCalcBtn.setOnClickListener(v->openAgeCalcActivity());
        openTypeAndCodeBtn.setOnClickListener(v -> openTypeAndCodeActivity());
    }

    //All these methods open the corresponding Activity(=class)

    private void openSearchActivity(){
        Intent openSearchIntent = new Intent(this,SearcherActivity.class);
        startActivity(openSearchIntent);
    }

    private void openCategoryViewActivity() {
        Intent openChirIntent = new Intent(this, CategoryViewActivity.class);
        startActivity(openChirIntent);
    }

    private void openTypeAndCodeActivity(){
        Intent openTypeAndCodeIntent = new Intent(this,TypeAndCodeActivity.class);
        startActivity(openTypeAndCodeIntent);
    }


    private void openAgeCalcActivity(){
        Intent openAgeCalcIntent = new Intent(this,AgeCalcActivity.class);
        startActivity(openAgeCalcIntent);
    }

    public static int getKat() {
        return kat;
    }
}
