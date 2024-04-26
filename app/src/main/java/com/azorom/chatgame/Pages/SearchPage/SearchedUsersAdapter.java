package com.azorom.chatgame.Pages.SearchPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azorom.chatgame.R;
import com.azorom.chatgame.Requests.User.FilteredUser;
import com.azorom.chatgame.Storage.DrawableSets;

public class SearchedUsersAdapter extends BaseAdapter {

    FilteredUser users[];
    LayoutInflater inflater;

    private OnSearchedUserClick onSearchedUserClickListener;

    public void setOnSearchedUserClickListener(OnSearchedUserClick listener){
        this.onSearchedUserClickListener = listener;
    }

    public SearchedUsersAdapter(Context context, FilteredUser[] users){
        this.users = users;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.length;
    }

    @Override
    public FilteredUser getItem(int i) {
        return users[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FilteredUser user = users[i];
        view = inflater.inflate(R.layout.searched_user_row, null);

        ImageView hatImg = view.findViewById(R.id.searchedUserHat);
        hatImg.setImageResource(DrawableSets.hats.get(user.character.hat));

        ImageView headImg = view.findViewById(R.id.searchedUserHead);
        headImg.setImageResource(DrawableSets.heads.get(user.character.head));

        TextView userNameLabel = view.findViewById(R.id.searchedUserNameLabel);
        userNameLabel.setText(user.userName);

        view.setOnClickListener(v-> {
            if(this.onSearchedUserClickListener != null){
                this.onSearchedUserClickListener.call(user);
            }
        });

        return view;
    }
}
