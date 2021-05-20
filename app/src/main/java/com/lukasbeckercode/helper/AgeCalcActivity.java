/*
This is the Age Calculator Class
*/
package com.lukasbeckercode.helper;

//imports handled by intelliJ
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class AgeCalcActivity extends AppCompatActivity {
    //the text-fields
    private EditText date;
    private EditText ageLabel;
    public static boolean isSubstracted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.age_calc);

        Button backBtn = findViewById(R.id.backBtnAge); //generic "back" button
        Button calcDateBtn = findViewById(R.id.buttonClac);
        //assign textfields
        date = findViewById(R.id.editTextDate);
        ageLabel = findViewById(R.id.editTextAge);
        backBtn.setOnClickListener(v->goBack()); //go back to main menu

        calcDateBtn.setOnClickListener((v)->{

            String getDate ; //Date
            String getDay = "0"; //Day to be extracted out of Date
            String getMonth ="0"; //Month to be extracted out of Date
            String getYear="0"; //Year to be extracted out of Date


            if(!date.getText().toString().equals("")) //Check if something is written inside the field
            {
                getDate =  date.getText().toString(); //save the input to the local variable
                try { //users are stupid, so we use this to avoid the app crashing

                    //There are two allowed separators / and . :
                    if(getDate.contains(".")&&getDate.indexOf(".") !=getDate.lastIndexOf(".") ) { //if the . is used

                        getDay = getDate.substring(0, getDate.indexOf(".")); //first number in front of the .
                        getMonth = getDate.substring(getDate.indexOf(".") + 1, getDate.lastIndexOf(".")); //number between the two .
                        getYear = getDate.substring(getDate.lastIndexOf(".") + 1); //number after the last .
                    } else if (getDate.contains("/")) // do the same thing but with /
                    {
                        getDay = getDate.substring(0, getDate.indexOf("/"));
                        getMonth = getDate.substring(getDate.indexOf("/") + 1, getDate.lastIndexOf("/"));
                        getYear = getDate.substring(getDate.lastIndexOf("/") + 1);

                    } else if(!getDate.isEmpty()) { //DDMMYYYY format
                        getDay = getDate.substring(0,2);
                        getMonth = getDate.substring(2,4);
                        getYear = getDate.substring(4);

                    }
                    else ageLabel.setText(R.string.age_invalid); //if something weird is inputted, tell the user they messed up

                } catch (Exception e) {

                    e.printStackTrace(); //Debugging, REMOVE FOR FINAL BUILD
                    ageLabel.setText(R.string.age_invalid);//if something weird is inputted, tell the user they messed up
                }
                try {
                    int i = calcAge(Integer.parseInt(getDay),Integer.parseInt(getMonth), Integer.parseInt(getYear)); //call the method calcAge, parse the Strings to ints
                    ageLabel.setText(String.valueOf(i)); //tell the user the age
                    if(ageLabel.getText().toString().equals("-1"))
                    {
                        ageLabel.setText(R.string.date_invalid);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace(); //Debugging, REMOVE FOR FINAL BUILD
                    ageLabel.setText(R.string.age_invalid);//if something weird is inputted, tell the user they messed up
                }


            } else {
                ageLabel.setText(R.string.age_invalid);//if something weird is inputted, tell the user they messed up
            }



            System.out.println(getDay+"/"+getMonth+"/"+getYear);//DEBUGGING
        });
    }
    public int calcAge(int d, int m, int y) //this method calculates the age
    {
        int age;

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault()); //create a calender object that uses the devices default time zone
        int curDay = calendar.get(Calendar.DAY_OF_MONTH); //get the current day
        int curMonth = calendar.get(Calendar.MONTH)+1; //Months are indexed from 0!
        int curYear = calendar.get(Calendar.YEAR); //get the current year



        System.out.println(curDay+"/"+curMonth+"/"+curYear);//DEBUGGING

        //if the user enters YY instead of YYYY, we have to do this:
        if(y<1000 && y>19 ) //below 1000 means YY, above 19 means the person is probably old
        {
            y +=1900;
        }else if (y<19) // below 1000 = below 19 means YY, below 19 means the person is probably a child (CAVE: or very old)
        {
            y +=2000;
        }
        age = curYear - y; // calculate the age

        //if the person hadn´t had their birthday yet, we need to subtract 1 year from their age
        if(m>curMonth) //if the month of their birthday hast´nt already been
        {
            --age; //substract one year from the age
            isSubstracted = true;

        } else if (m == curMonth){
            if (d > curDay) { //and their birthday isn´t yet reached
                --age; //substract one year from the age
                isSubstracted = true;
            }
        }

        if(age < 0 || age > 120)
        {
            age = -1;
        }

        return age; //return the age
    }
    private void goBack() //generic "return to main menu method
    {
        Intent goBackIntent = new Intent(this,StartUpActivity.class);
        startActivity(goBackIntent);
    }
}
