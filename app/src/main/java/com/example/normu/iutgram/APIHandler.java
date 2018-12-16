package com.example.normu.iutgram;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.net.InetAddress;

public class APIHandler {

    private Context context;
    private String url;
    private static boolean returnValue;

    public APIHandler(Context context){
        this.context=context;
        this.url="http://192.168.100.101/iutgram/API";
    }


    public boolean checkConnection(){
        /*try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }*/
    return true;
    }

}
