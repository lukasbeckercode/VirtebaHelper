package com.lukasbeckercode.virtebahelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TypeAndCodeActivity extends AppCompatActivity {

    private InputStream streamCodes;
    private InputStream streamType;
    private BufferedReader bufferedReaderCodes;
    private BufferedReader bufferedReaderType;

    private static final String [] type = new String[27];
    private static final String [] typeNum = new String[27];
    private static final String [] typeText = new String[27];
    private static final String [] codes = new String[53];
    private static final String [] codesNum = new String[53];
    private static final String [] codesText = new String[53];
    private EditText typeAndCodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_and_code);
        Button backBtn = findViewById(R.id.backBtnType);
        typeAndCodeText = findViewById(R.id.editTextType);
        TabLayout tabs = findViewById(R.id.tabLayout1);
        typeAndCodeText.setEnabled(false);

        readCodesData();
        readTypeData();

        int curTab = tabs.getSelectedTabPosition();
        if(curTab == 0)
        {
            writeTypeText();
        }else if(curTab == 1){
           writeCodesText();
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
             int curTab = tabs.getSelectedTabPosition();
             if(curTab == 0)
             {
                 writeTypeText();
             }else if(curTab == 1){
                writeCodesText();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        backBtn.setOnClickListener(v->goBack());
    }
    private void goBack()
    {
        Intent goBackIntent = new Intent(this,StartUpActivity.class);
        startActivity(goBackIntent);
    }

    private void readCodesData()
    {
        streamCodes = this.getResources().openRawResource(R.raw.stats);
        bufferedReaderCodes = new BufferedReader(new InputStreamReader(streamCodes));
        final Thread thread = new Thread(()->{
            if(streamCodes != null){
                try {
                    String data;
                    for(int i = 0; (data= bufferedReaderCodes.readLine()) != null; i++)
                    {
                        codes[i] = data;
                    }

                    for(int i = 0; i<codes.length;i++){
                        codesNum[i] = codes[i].substring(0,codes[i].indexOf("---"));
                        codesText[i] = codes[i].substring(codes[i].indexOf("---")+3);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while (thread.isAlive()){

        }
    }
    private void readTypeData(){
        streamType = this.getResources().openRawResource(R.raw.type);
        bufferedReaderType = new BufferedReader(new InputStreamReader(streamType));
        final Thread thread = new Thread(()->{
            if(streamType != null){
                try {
                    String data;
                    for(int i = 0; (data=bufferedReaderType.readLine()) != null; i++){
                        type[i] = data;
                    }

                    for(int i = 0; i<type.length; i++){
                        typeNum[i]= type[i].substring(0,type[i].indexOf("---"));
                        typeText[i]=type[i].substring(type[i].indexOf("---")+3);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });

        thread.start();
        while (thread.isAlive()){

        }
    }

    private void writeCodesText()
    {
        typeAndCodeText.setText("");
        for(String s:codesNum)
        {
            if(s != null)
            {
                int pos = Arrays.asList(codesNum).indexOf(s);
                typeAndCodeText.append(s+": "+codesText[pos]+"\n");
            }
        }
    }
    private void writeTypeText()
    {
        typeAndCodeText.setText("");
        for(String s:typeNum)
        {
            if(s != null)
            {
                int pos = Arrays.asList(typeNum).indexOf(s);
                typeAndCodeText.append(s+": "+typeText[pos]+"\n");
            }
        }
    }
}
