package com.azorom.chatgame.Pages.ChatPage.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.azorom.chatgame.Pages.ChatPage.MessageViewModel;
import com.azorom.chatgame.R;

public class SendMessageFragment extends Fragment {

    public SendMessageFragment() {
        // Required empty public constructor
    }

    EditText messageInput;
    Button sendBtn;

    MessageViewModel messageViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_message, container, false);
        messageViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);

        messageInput = view.findViewById(R.id.messageInput);
        sendBtn = view.findViewById(R.id.sendMsgBtn);

        Button stickerPickerBtn = view.findViewById(R.id.stickerBtn);
        stickerPickerBtn.setOnClickListener(v -> messageViewModel.switchToStickerPicker());

        handleMessageInput();

        return view;
    }

    private void handleMessageInput(){
        messageInput.setOnKeyListener((view, i, keyEvent) -> {
            if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)){
                messageViewModel.setContent(messageInput.getText().toString());
                messageInput.setText("");
                messageViewModel.sendMessage();
            }
            return false;
        });
        sendBtn.setOnClickListener(v -> {
            messageViewModel.setContent(messageInput.getText().toString());
            messageInput.setText("");
            messageViewModel.sendMessage();
        });
    }

}