package com.example.normu.iutgram;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {

    private ListView chatListView;
    private List<Chat> chats;
    private ChatListViewAdapter ChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        chats = new ArrayList<>();


        fn();

    }

    public void fn() {
        Ion.with(ListActivity.this)
                .load(MainActivity.BASE_URL + "/getInitMessages")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e1, JsonObject result_inner) {

                        JsonParser parser = new JsonParser();
                        JsonElement jsonTree = parser.parse(String.valueOf(result_inner));

                        System.out.println(jsonTree);

                        JsonObject jsonObject = null;

                        if (jsonTree.isJsonObject()) {
                            jsonObject = jsonTree.getAsJsonObject();
                            if (jsonObject.get("ok").getAsBoolean()) {
                                System.out.println("HERE: -- loggedin");
                                JsonElement jsonTreeAray = parser.parse(String.valueOf(jsonObject.get("respond")));
                                JsonArray jsonArray = null;
                                JsonObject jsonObjectChat = null;
                                if (jsonTreeAray.isJsonArray()) {
                                    jsonArray = jsonTreeAray.getAsJsonArray();
                                    TextDrawable drawable = null;

                                    for (int i = 0; i < jsonArray.size(); i++) {

                                        System.out.println(jsonArray.get(i));

                                        JsonElement jsonTreeAray1 = parser.parse(String.valueOf(jsonArray.get(i)));
                                        jsonObjectChat = jsonTreeAray1.getAsJsonObject();

                                        Random rnd = new Random();
                                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                                        drawable = TextDrawable.builder()
                                                .buildRound(jsonObjectChat.get("name").getAsString().substring(0, 1).toUpperCase(), color);

                                        Chat ch = new Chat(jsonObjectChat.get("user_id").getAsString(),
                                                String.valueOf(getFormattedDateTime(jsonObjectChat.get("the_date").getAsString())),
                                                jsonObjectChat.get("name").getAsString(),
                                                cutMsg(getFormattedMsg(
                                                        "last_message",
                                                        "media_extension",
                                                        jsonObjectChat
                                                )),
                                                drawable);
                                        chats.add(ch);
                                    }

                                    chatListView = (ListView) findViewById(R.id.chatList);
                                    ChatAdapter = new ChatListViewAdapter(ListActivity.this, chats);
                                    chatListView.setAdapter(ChatAdapter);
                                }
                            } else {
                                Toast.makeText(ListActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private String getFormattedMsg(String last_message, String media, JsonObject jsonObjectChat) {
        if (!jsonObjectChat.get(media).isJsonNull()) {
            return jsonObjectChat.get(media).getAsString();
        }
        return jsonObjectChat.get(last_message).getAsString();
    }

    public String getFormattedDateTime(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDate = null;
        try {
            Date date = format.parse(input);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

    public String cutMsg(String msg) {
        if (msg.length() > 20) {
            return msg.substring(0, 20) + "...";
        }
        return msg;
    }


}