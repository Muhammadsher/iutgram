package com.example.normu.iutgram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Mukhammadsher on 12/12/2018.
 */

public class ChatListViewAdapter extends ArrayAdapter<Chat>{

    private List<Chat> chatList;
    private Context context;

    public ChatListViewAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
    }


    public ChatListViewAdapter(@NonNull Context context, List<Chat> chats){
        super(context, R.layout.list_item);
        this.chatList = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ChatHolder chatHolder = new ChatHolder();

        final Chat chat = chatList.get(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        chatHolder.username = convertView.findViewById(R.id.username_chat_list);
        chatHolder.lastMessage = convertView.findViewById(R.id.lastMessage);
        chatHolder.image = convertView.findViewById(R.id.userImage);
        chatHolder.the_date = convertView.findViewById(R.id.the_date);

        chatHolder.username.setText(chat.getName());
        chatHolder.lastMessage.setText(chat.getLast_message());
        chatHolder.image.setImageDrawable(chat.getUserImage());
        chatHolder.the_date.setText(chat.getThe_date());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("HERE item--");
                System.out.println(chat.getUser_id());
                Intent I = new Intent(context, ChatActivity.class);
                I.putExtra("user_id", chat.getUser_id());
                context.startActivity(I);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    private class ChatHolder{

        private TextView username;
        private TextView lastMessage;
        private ImageView image;
        private TextView the_date;

    }
}
