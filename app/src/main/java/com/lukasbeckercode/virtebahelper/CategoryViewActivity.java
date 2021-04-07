package com.lukasbeckercode.virtebahelper;

//imports handled by IntelliJ

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;
import java.util.TreeMap;

/**
 * <title>CategoryViewActivity </title>
 * <summary>This view adapts to the selected Category and
 * displays the according Code:Diagnosis pairs</summary>
 *
 * @author lukasbecker
 */
public class CategoryViewActivity extends AppCompatActivity {

    private TreeMap<Integer,String> allCodes = new TreeMap<>();
    TreeMap<Integer, String> codes = new TreeMap<>(); //TreeMap automatically sorts by key

    /**
     * gets executed each time this class is called
     * @param savedInstanceState android native param
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CodeHandler codeHandler = new CodeHandler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_view);

        Button backBtn = findViewById(R.id.backBtnCat); //generic "back" button
        EditText text = findViewById(R.id.editTextCat);
        text.setEnabled(false); // better design

        allCodes = codeHandler.getAllCodes();
        mapCodes(StartUpActivity.getKat());

        for (Map.Entry<Integer, String> entry : codes.entrySet()) {
            @SuppressLint("DefaultLocale") String s = String.format("%d: %s%n", entry.getKey(), entry.getValue());
            text.append(s);
        }

        backBtn.setOnClickListener(v -> goBack());
    }

    /**
     * to go back to the previous Activity
     */
    private void goBack() {
        Intent goBackIntent = new Intent(this, StartUpActivity.class);
        startActivity(goBackIntent);
    }

    /**
     * maps the codes to the selected Category
     * @param cat the selected category
     */
    void mapCodes(int cat){
        allCodes.forEach((k,v)->{

            if(cat == 3&&(k/10==41||k/10==42)){
               codes.put(k,v);
            }else if (cat == 7&&k/10==43){
                codes.put(k,v);
            }else if(cat==6&&(k/100 == 1||k/100==7)){
                codes.put(k,v);
            }else if(k/100==cat+1){
                int cat_decoder = k/10;
                boolean isChecked = cat_decoder == 41||cat_decoder==42||cat_decoder==43;

                if(!isChecked)
                    codes.put(k,v);
            }
        });
    }
}
