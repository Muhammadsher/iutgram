package com.example.normu.iutgram;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    private APIHandler handler;

    @BindView(R.id.name_register) EditText registrationName;
    @BindView(R.id.email_register) EditText registrationEmail;
    @BindView(R.id.password_register) EditText registrationPassword;
    @BindView(R.id.c_password_register) EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        handler=new APIHandler(this);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_register)
    protected void createAccount(){

        String name=registrationName.getText().toString();
        String id=registrationEmail.getText().toString().trim();
        String password=registrationPassword.getText().toString().trim();

        if(password.equals(confirmPassword.getText().toString().trim())){
            System.out.println("if here");
            Ion.with(this)
                .load(MainActivity.BASE_URL+"/register")
                .setBodyParameter("name", name)
                .setBodyParameter("password", password)
                .setBodyParameter("userID", id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        //System.out.println("HERE: "+result.get("ok").toString());
                        JsonParser parser = new JsonParser();
                        JsonElement jsonTree = parser.parse(String.valueOf(result));
                        System.out.println(jsonTree);
                        JsonObject jsonObject = null;
                        if(jsonTree.isJsonObject()) {
                            jsonObject = jsonTree.getAsJsonObject();
                            if(jsonObject.get("ok").getAsBoolean()){
                                System.out.println("HERE: -- registered");
                                Intent intent=new Intent(Register.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Register.this, "This user id already exists or there is something wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        }else{
            Toast.makeText(Register.this, "Confirm password doesnt match!", Toast.LENGTH_SHORT).show();
        }
    }
}

