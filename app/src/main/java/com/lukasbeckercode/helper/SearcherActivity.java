/*
This is the Searcher Class, in here, you can look for codes by entering Keywords
We are reading the pzc.txt file separately here to improve performance
*/

package com.lukasbeckercode.helper;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;

import android.text.method.ScrollingMovementMethod;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;


public class SearcherActivity extends AppCompatActivity {

    //UI Variables
    private AutoCompleteTextView textView;
    private EditText number;



    private final List<String> codesComplete = new ArrayList<>();
    private final List<String> code =new ArrayList<>();
    private final List<String> diag = new ArrayList<>();

    private InputStream stream; //a stream reader that reads a text file with all the diagnosis inside
    private BufferedReader reader;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searcher); //set the view to activity.main

        //Add the UI Items
        textView= findViewById(R.id.autoTextView); //AutoFillTextField
        number = findViewById(R.id.pzcLabel); //displays the pzc

        Button backBtn = findViewById(R.id.backBtnSearcher); //returns to the main menu

        textView.setMovementMethod(new ScrollingMovementMethod()); //make the textView scrollable


        stream = this.getResources().openRawResource(R.raw.pzc); //get the file
        reader = new BufferedReader(new InputStreamReader(stream));


        final Thread readThread = new Thread(()->{ //Lambda Expression!
            if (stream != null) {
                try {

                    // a var to save the read data to
                    String data;
                    for (int i = 0; (data = reader.readLine()) != null; i++) //read until every line is finished
                    {
                        codesComplete.add(data); //add the data to the Array
                    }

                    for (int i = 0; i < codesComplete.size(); i++) {
                        String currElement = codesComplete.get(i);
                        String tempCode = currElement.substring(0,currElement.indexOf("---"));
                        String tempDiag = currElement.substring(currElement.indexOf("---")+3);
                        code.add(tempCode);
                        diag.add(tempDiag);
                    }


                    stream.close(); //close the stream


                } catch (Exception e) {
                    e.printStackTrace(); //Remove for build!
                }
            }
        });
        readThread.start(); //start the thread
        //noinspection StatementWithEmptyBody
        while (readThread.isAlive()) //wait until the thread is done
        {
           // System.out.print('.'); For Debugging
        }

        //convert pzc.txt to fit into the textView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diag);
        textView.setAdapter(adapter);

        //Listener for when somebody clicks any item of the textView
     textView.setOnItemClickListener((parent, view, position, id) -> {
        String selectedItem = textView.getText().toString(); //save the selected item to a local variable
         int pos = diag.indexOf(selectedItem);  // save the position of the selection within the Array to pos
         number.setText(code.get(pos)); //display the corresponding number to the chosen item

     });

        //Listener for when somebody clicks the Button

        backBtn.setOnClickListener(v->goBack());

    }


    private void goBack() //standard method to return to main menu
    {
        Intent goBackIntent = new Intent(this,StartUpActivity.class);
        startActivity(goBackIntent);
    }
}
