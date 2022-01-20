package com.example.mnet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class InfoCard {
    Map<String,String> md = new LinkedHashMap<String,String>();
    InfoCard(Map<String,String> pd, JSONObject mj){

        for (Map.Entry<String,String> entry : pd.entrySet()){
           md.put(entry.getKey(),entry.getValue() );
        }
        md.put("sep","sep");
    try{
        for (Iterator<String> it = mj.keys(); it.hasNext(); ) {
            String key = it.next();
            md.put(key,mj.getString(key));
        }
        }catch(Exception e){
         e.printStackTrace();
        }

        for (Map.Entry<String,String> entry : md.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }


    }

}
