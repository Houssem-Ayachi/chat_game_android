package com.azorom.chatgame.Pages.HomePage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azorom.chatgame.R;
import com.azorom.chatgame.Requests.Chat.OnlineChat;
import com.azorom.chatgame.Storage.DrawableSets;

public class OnlineChatsAdapter extends RecyclerView.Adapter<OnlineChatsAdapter.Holder> {

    OnlineChat[] onlineChats;
    private final OnOnlineChatClickListener itemClickListener;
    public OnlineChatsAdapter(OnlineChat[] onlineChats, OnOnlineChatClickListener itemClickListener){
        this.onlineChats = onlineChats;
        this.itemClickListener = itemClickListener;
    }

    static class Holder extends  RecyclerView.ViewHolder{

        ImageView hatImg, headImg;
        TextView userNameLabel;

        View view;

        Holder(View view){
            super(view);
            hatImg = view.findViewById(R.id.onlineFriendHat);
            headImg = view.findViewById(R.id.onlineFriendHead);
            userNameLabel = view.findViewById(R.id.onlineFriendName);
            this.view = view;
        }

        public void setOnClickListener(View.OnClickListener listener){
            this.view.setOnClickListener(listener);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_chat_description, null);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        OnlineChat onlineChat = onlineChats[position];
        holder.userNameLabel.setText(onlineChat.friend.userName);
        holder.hatImg.setImageResource(DrawableSets.hats.get(onlineChat.friend.character.hat));
        holder.headImg.setImageResource(DrawableSets.heads.get(onlineChat.friend.character.head));
        holder.setOnClickListener(v -> this.itemClickListener.OnClick(onlineChat));
    }

    @Override
    public int getItemCount() {
        return onlineChats.length;
    }
}
