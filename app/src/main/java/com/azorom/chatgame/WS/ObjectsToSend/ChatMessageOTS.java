package com.azorom.chatgame.WS.ObjectsToSend;

public class ChatMessageOTS{
    public String chatId;
    public String senderId;
    public String sticker;

    public ChatMessageOTS(String chatId, String senderId, String sticker){
        this.chatId = chatId;
        this.senderId = senderId;
        this.sticker = sticker;
    }
}
