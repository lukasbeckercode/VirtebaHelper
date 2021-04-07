package com.lukasbeckercode.virtebahelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;

/**
 * <title>CodeHandler</title>
 * <summary>Handles the reading of the textfile containing the code:diagnosis pairs</summary>
 * TODO: Simplify (TreeMap?)
 **/
public class CodeHandler {
    private static final TreeMap<Integer,String> allCodesMap = new TreeMap<>();
    private InputStream stream;

    /**Default Constructor*/
    public CodeHandler(){

    }

    /**
     * Constructor handling the InputStream that is needed to read the pzc file
     * @param stream an InputStream reading the pzc file
     * */
    public CodeHandler(InputStream stream){
        this.stream = stream;
    }

    /**
     * @return the allCodes List
     */
    public TreeMap<Integer,String> getAllCodes(){
        return allCodesMap;
    }

    /**
     * reads the pcz file and parses it
     */
    protected void readData(){
        //a stream reader that reads a text file with all the diagnosis inside
        //get the file
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        final Thread readThread = new Thread(()->{
            if (stream != null) {
                try {

                    // a var to save the read data to
                    String data;
                    for (int i = 0; (data = reader.readLine()) != null; i++) //read until every line is finished
                    {
//                        compCodes.add(data); //add the data to the Array
                        int key = Integer.parseInt(data.substring(0,data.indexOf("---")));
                        String val = data.substring(data.indexOf("---")+3);
                        allCodesMap.put(key,val);
                    }

 //                   for (String code:compCodes) {
 //                       allCodes.add(code.substring(0, code.indexOf("---"))); //read the code
 //                       allDiag.add(code.substring(code.indexOf("---") + 3)); //read the diagnosis
 //                   }

                    stream.close(); //close the stream

                } catch (Exception e) {
                    e.printStackTrace(); //remove for final build
                }
            }
        });
        readThread.start();
        while (readThread.isAlive()) //wait for the thread to finish(possibly useless, IDK)
        {

        }
    }
}
