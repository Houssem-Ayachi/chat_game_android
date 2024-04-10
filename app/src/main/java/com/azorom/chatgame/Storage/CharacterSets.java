package com.azorom.chatgame.Storage;

import com.azorom.chatgame.R;

import java.util.Map;

public class CharacterSets {
    public static Map<String, Integer> hats = Map.ofEntries(
            Map.entry("hat1", R.drawable.hat1),
            Map.entry("hat2", R.drawable.hat2),
            Map.entry("hat3", R.drawable.hat3)
    );

    public static Map<String, Integer> heads = Map.ofEntries(
            Map.entry("head1", R.drawable.head1),
            Map.entry("head2", R.drawable.head2),
            Map.entry("head3", R.drawable.head3)
    );
}
