package com.azorom.chatgame.Pages.HomePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azorom.chatgame.R;
import com.azorom.chatgame.Requests.Chat.ChatRow;
import com.azorom.chatgame.Storage.DrawableSets;

public class ActiveChatsAdapter extends BaseAdapter {

    private ChatRow[] chatRows;
    LayoutInflater inflater;

    public ActiveChatsAdapter(Context context, ChatRow[] chatRows){
        this.chatRows = chatRows;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chatRows.length;
    }

    @Override
    public ChatRow getItem(int i) {
        return chatRows[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatRow chat = chatRows[i];
        view = inflater.inflate(R.layout.conversation_row, null);

        ImageView hat = view.findViewById(R.id.convRowHat);
        hat.setImageResource(DrawableSets.hats.get(chat.user.character.hat));

        ImageView head = view.findViewById(R.id.convRowHead);
        head.setImageResource(DrawableSets.heads.get(chat.user.character.head));

        TextView userNameLabel = view.findViewById(R.id.userNameLabel);
        userNameLabel.setText(chat.user.userName);

        TextView lastMessage = view.findViewById(R.id.lastMessage);
        lastMessage.setText(chat.lastMessage.content);

        return view;
    }
}