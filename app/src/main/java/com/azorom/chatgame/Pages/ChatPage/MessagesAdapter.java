package com.azorom.chatgame.Pages.ChatPage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azorom.chatgame.R;
import com.azorom.chatgame.Requests.Chat.ChatMessage;
import com.azorom.chatgame.Storage.DrawableSets;

public class MessagesAdapter extends BaseAdapter {

    ChatMessage[] messages;
    LayoutInflater inflater;

    public MessagesAdapter(Context context, ChatMessage[] messages){
        this.messages = messages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messages.length;
    }

    @Override
    public ChatMessage getItem(int i) {
        return messages[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(!messages[i].sticker.equals("")){
            view = createMessageWithSticker(view, messages[i]);
        }else{
            view = createSimpleMessage(view, messages[i]);
        }
        return view;
    }

    private View createMessageWithSticker(View view, ChatMessage message){
        view = inflater.inflate(R.layout.chat_message_with_sticker, null);
        ImageView hat = view.findViewById(R.id.chatMsgStickerHat);
        hat.setImageResource(DrawableSets.hats.get(message.user.character.hat));
        ImageView head = view.findViewById(R.id.chatMsgStickerHead);
        head.setImageResource(DrawableSets.heads.get(message.user.character.head));
        TextView content = view.findViewById(R.id.chatMsgStickerContent);
        content.setText(message.content);
        ImageView sticker = view.findViewById(R.id.chatMessageSticker);
        sticker.setImageResource(DrawableSets.stickers.get(message.sticker));
        return view;
    }
    private View createSimpleMessage(View view, ChatMessage message){
        view = inflater.inflate(R.layout.chat_message, null);
        ImageView hat = view.findViewById(R.id.chatMsgStickerHat);
        hat.setImageResource(DrawableSets.hats.get(message.user.character.hat));
        ImageView head = view.findViewById(R.id.chatMsgStickerHead);
        head.setImageResource(DrawableSets.heads.get(message.user.character.head));
        TextView content = view.findViewById(R.id.chatMsgStickerContent);
        content.setText(message.content);
        return view;
    }
}
