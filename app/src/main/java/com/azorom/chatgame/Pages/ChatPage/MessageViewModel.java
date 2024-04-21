package com.azorom.chatgame.Pages.ChatPage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.azorom.chatgame.Pages.ChatPage.Interfaces.SendMessageListener;
import com.azorom.chatgame.Pages.ChatPage.Interfaces.SwitchToMessageListener;
import com.azorom.chatgame.Pages.ChatPage.Interfaces.SwitchToStickerPickerListener;
import com.azorom.chatgame.Requests.Chat.CreateMsgObj;

public class MessageViewModel extends ViewModel {
    private final MutableLiveData<String> content = new MutableLiveData<>();
    private final MutableLiveData<String> sticker = new MutableLiveData<>();

    private String chatId = "";
    private SendMessageListener sendMessageListener;
    private SwitchToStickerPickerListener switchToStickerPickerListener;
    private SwitchToMessageListener switchToMessageListener;

    public MessageViewModel(){
        content.setValue("");
        sticker.setValue("");
    }

    public void setChatId(String chatId){
        this.chatId = chatId;
    }

    public void setContent(String content){
        this.content.setValue(content);
    }

    public void setSticker(String sticker){
        this.sticker.setValue(sticker);
    }

    public void setSendMessageListener(SendMessageListener listener){
        this.sendMessageListener = listener;
    }

    public void setSwitchToStickerPickerListener(SwitchToStickerPickerListener listener){
        switchToStickerPickerListener = listener;
    }

    public void setSwitchToMessageListener(SwitchToMessageListener listener){
        this.switchToMessageListener = listener;
    }

    public void switchToMessage(){
        if(this.switchToMessageListener != null){
            this.switchToMessageListener.switchToMessage();
        }
    }

    public void switchToStickerPicker(){
        if(this.switchToStickerPickerListener != null){
            this.switchToStickerPickerListener.switchToStickerPicker();
        }
    }

    public void sendMessage(){
        CreateMsgObj message = new CreateMsgObj(this.chatId, content.getValue(), sticker.getValue());
        if(this.sendMessageListener != null){
            this.sendMessageListener.sendMessage(message);
            this.sticker.setValue("");
        }
    }
}
