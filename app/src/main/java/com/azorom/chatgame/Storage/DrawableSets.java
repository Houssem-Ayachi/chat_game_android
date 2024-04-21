package com.azorom.chatgame.Storage;

import com.azorom.chatgame.R;

import java.util.Map;

public class DrawableSets {
    public static Map<String, Integer> hats = Map.ofEntries(
            Map.entry("hat1", R.drawable.hat1),
            Map.entry("hat2", R.drawable.hat2),
            Map.entry("hat3", R.drawable.hat3),
            Map.entry("hat4", R.drawable.hat4),
            Map.entry("hat5", R.drawable.hat5),
            Map.entry("hat6", R.drawable.hat6)
    );

    public static Map<String, Integer> heads = Map.ofEntries(
            Map.entry("head1", R.drawable.head1),
            Map.entry("head2", R.drawable.head2),
            Map.entry("head3", R.drawable.head3),
            Map.entry("head4", R.drawable.head4),
            Map.entry("head5", R.drawable.head5),
            Map.entry("head6", R.drawable.head6)
    );

    public static Map<String, Integer> stickers = Map.ofEntries(
            Map.entry("sticker1", R.drawable.hat1),
            Map.entry("sticker2", R.drawable.hat2),
            Map.entry("sticker3", R.drawable.hat3),
            Map.entry("sticker4", R.drawable.hat4),
            Map.entry("sticker6", R.drawable.hat5)
    );
}