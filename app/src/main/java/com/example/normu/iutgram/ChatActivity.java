package com.example.normu.iutgram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    private String user_id;
    @BindView(R.id.chat_container) LinearLayout chatContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        initMessages();
    }

    private void initMessages()
    {
        Ion.with(this)
                .load(MainActivity.BASE_URL+"/getConversation")
                .setBodyParameter("c_id", this.user_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonParser parser = new JsonParser();
                        JsonElement jsonTree = parser.parse(String.valueOf(result));
                        System.out.println("HERE chats");
                        System.out.println(jsonTree);
                        JsonObject jsonObject = null;
                        if(jsonTree.isJsonObject()) {
                            jsonObject = jsonTree.getAsJsonObject();
                            if(jsonObject.get("ok").getAsBoolean()){
                                JsonElement jsonTreeArray = parser.parse(String.valueOf(jsonObject.get("respond")));
                                JsonArray jsonArray = null;
                                JsonObject jsonObjectChat = null;
                                if (jsonTreeArray.isJsonArray()) {
                                    jsonArray = jsonTreeArray.getAsJsonArray();
                                    for (int i=0; i<jsonArray.size(); i++){

                                        System.out.println(jsonArray.get(i));

                                        JsonElement jsonTreeArray1 = parser.parse(String.valueOf(jsonArray.get(i)));
                                        jsonObjectChat = jsonTreeArray1.getAsJsonObject();
                                        if(jsonObjectChat.get("type").getAsString().equals("in")){
                                            View v = LayoutInflater.from(ChatActivity.this).inflate(R.layout.receiver_msg, null);
                                            TextView receiverMsgContent = v.findViewById(R.id.receiver_msg_content);
                                            receiverMsgContent.setText(ListActivity.getFormattedMsg("message", "media_extension",jsonObjectChat));
                                            TextView receiverMsgDatetime= v.findViewById(R.id.receiver_msg_datetime);
                                            receiverMsgDatetime.setText(getFormattedDateTime(jsonObjectChat.get("the_date").getAsString()));
                                            chatContainer.addView(v);

                                        }else{
                                            View v = LayoutInflater.from(ChatActivity.this).inflate(R.layout.sender_msg, null);
                                            TextView senderMsgContent = v.findViewById(R.id.sender_msg_content);
                                            senderMsgContent.setText(ListActivity.getFormattedMsg("message", "media_extension",jsonObjectChat));
                                            TextView senderMsgDatetime= v.findViewById(R.id.sender_msg_datetime);
                                            senderMsgDatetime.setText(getFormattedDateTime(jsonObjectChat.get("the_date").getAsString()));
                                            chatContainer.addView(v);
                                        }

                                    }
                                }
                            }else{
                                Toast.makeText(ChatActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    public static String getFormattedDateTime(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDate = null;
        try {
            Date date = format.parse(input);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date today = new Date();
            if (!dateFormat.format(today).equals(dateFormat.format(date))) {
                return String.valueOf(dateFormat.format(date));
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            formattedDate = formatter.format(date);
        } catch (ParseException e) {
            //e.printStackTrace();
            System.out.println("HERE:" + e.getMessage());
        }

        return formattedDate;
    }
}
