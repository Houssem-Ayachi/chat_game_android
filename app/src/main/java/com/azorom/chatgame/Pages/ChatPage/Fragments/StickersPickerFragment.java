package com.azorom.chatgame.Pages.ChatPage.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.azorom.chatgame.Pages.ChatPage.MessageViewModel;
import com.azorom.chatgame.R;
import com.azorom.chatgame.Storage.DrawableSets;

import java.util.Map;

public class StickersPickerFragment extends Fragment {

    LinearLayout stickersContainer;

    MessageViewModel messageViewModel;

    public StickersPickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stickers_picker, container, false);
        messageViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);
        stickersContainer = view.findViewById(R.id.stickersContainer);
        fillStickers();
        return view;
    }

    //TODO: fill stickers from db (user's actual stickers)
    private void fillStickers(){
        Map<String, Integer> stickers = DrawableSets.stickers;
        stickers.forEach((name, id) -> {
            View stickerView = createStickerView(name, id);
            stickersContainer.addView(stickerView);
        });
    }

    private View createStickerView(String name, int id){
        View view = View.inflate(this.getActivity(), R.layout.sticker_container, null);
        ImageView stickerImg = view.findViewById(R.id.stickerImg);
        stickerImg.setImageResource(id);
        view.setOnClickListener(v -> {
            messageViewModel.setSticker(name);
            messageViewModel.switchToMessage();
        });
        return view;
    }
}