package com.example.mnet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JDownload implements Runnable {
    private final String loginUrl = "https://raw.githubusercontent.com/optile/checkout-android/develop/shared-\n" +
            "test/lists/listresult.json";
    private  String response;
    private Activity mc;
    private ProgressDialog mv;
    private SharedPreferences mpref;
    private MHandler mh;


    JDownload(Activity c, ProgressDialog m, MHandler x){ mc = c; mv=m;
        mpref= mc.getSharedPreferences("mp",Context.MODE_PRIVATE);
        mh = x;
    }


    public void run() {
        JSONObject json=null;
        try {
            URL url = new URL(loginUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setDoInput(true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
           bufferedReader.mark(1000000);
            //read your bufferdInputStream
            int cnt=0;





//            ProgressBar mp = (ProgressBar) mv.findViewById(R.id.simpleProgressBar);

            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
            }

            mv.setMax(cnt);
            cnt=0;
            bufferedReader.reset();


            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
                mv.setProgress(++cnt);
                Thread.sleep(3);
            };


            mv.dismiss();
           // json = new JSONObject(new JSONTokener(stringBuilder.toString()));
            mh.fArr(stringBuilder.toString());
           // mpref.edit().putString("json",stringBuilder.toString());
          ///  mh.rJson();

        } catch (Exception e) {
            e.printStackTrace();
        }
       // return json;
    }
}
