package com.example.normu.iutgram;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL="http://192.168.0.50/iutgram/API";

    @BindView(R.id.username_login) EditText username;
    @BindView(R.id.password_login) EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    protected void login(){
        String id=username.getText().toString().trim();
        String pass=password.getText().toString().trim();

        Ion.with(this)
                .load(MainActivity.BASE_URL+"/login")
                .setBodyParameter("userID", id)
                .setBodyParameter("password", pass)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonParser parser = new JsonParser();
                        JsonElement jsonTree = parser.parse(String.valueOf(result));
                        System.out.println("HERE it is");
                        System.out.println(jsonTree);
                        JsonObject jsonObject = null;
                        if(jsonTree.isJsonObject()) {
                            jsonObject = jsonTree.getAsJsonObject();
                            if(jsonObject.get("ok").getAsBoolean()){
                                System.out.println("HERE: -- loggedin");

                                Intent intent=new Intent(MainActivity.this,ListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "User id or password is wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    @OnClick(R.id.register_login)
    protected void openRegisterActivity(){
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void checkConnection(){
        if(isOnline()){
            Toast.makeText(MainActivity.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

}
