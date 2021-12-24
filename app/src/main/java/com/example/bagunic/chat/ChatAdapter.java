package com.example.bagunic.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bagunic.R;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {
    private ArrayList<ChatVO> items = new ArrayList<ChatVO>();


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void additem(String nick, String chat, String day) {
        ChatVO item = new ChatVO(nick, chat, day);
        items.add(item);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        //view(listview)에 우리가 만든 xml을 적용시키기
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatlist, parent, false);
        }

        ChatVO member = items.get(position);
        TextView nick = convertView.findViewById(R.id.chatnick);
        TextView chat = convertView.findViewById(R.id.chatcontent);

        nick.setText(member.getNick());
        chat.setText(member.getChat());


        return convertView;
    }


}
