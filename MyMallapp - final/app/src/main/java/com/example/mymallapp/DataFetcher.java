package com.example.mymallapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataFetcher {
    public static String fetchUrl(String url2){
        String line;
        StringBuffer sb=new StringBuffer();
        try {
            BufferedReader br;
            //DataFetcher.fetchUrl();
            //String url2="https://jsonplaceholder.typicode.com/todos/1";
            URL url=new URL(url2);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int status=conn.getResponseCode();
            if(status>200) {
                br= new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                return "";
            }
            else {
                br= new BufferedReader(new InputStreamReader(conn.getInputStream()));

            }
            while((line=br.readLine())!=null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String fetchUrl(String url2,String param){
        String line;
        StringBuffer sb=new StringBuffer();
        try {
            BufferedReader br;
            //DataFetcher.fetchUrl();
            //String url2="https://jsonplaceholder.typicode.com/todos/1";
            URL url=new URL(url2);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());

            String urlPostParameters = param;
            outputStream.writeBytes(urlPostParameters);

            outputStream.flush();
            outputStream.close();
            int status=conn.getResponseCode();
            if(status>200) {
                br= new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                return "";
            }
            else {
                br= new BufferedReader(new InputStreamReader(conn.getInputStream()));

            }
            while((line=br.readLine())!=null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void goToUrl(Activity aa, String s){
        Uri uu= Uri.parse(s);
        aa.startActivity(new Intent(Intent.ACTION_VIEW,uu));
    }
}
