package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.SearchedUser;
import com.azorom.chatgame.Requests.User.UserRequest;
import com.azorom.chatgame.Storage.CharacterSets;

public class SearchPage extends AppCompatActivity {

    UserRequest userReqHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        userReqHandler = new UserRequest(this.getApplicationContext());

        SearchView searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchUser(s);
                return false;
            }
        });
    }

    private void searchUser(String userName){
        if(!userName.equals("")){
            RequestResponse<SearchedUser[]> resp = userReqHandler.searchUser(userName);
            if(resp.error != null){
                Log.d("DEBUG", resp.error.message);
            }else if(resp.response != null){
                fillUserRows(resp.response);
            }
        }else{
            fillUserRows(new SearchedUser[]{});
        }
    }

    private void fillUserRows(SearchedUser[] users){
        LinearLayout container = findViewById(R.id.SearchedUsersRows);
        container.removeAllViews();
        for(SearchedUser user: users){
            View row = View.inflate(this, R.layout.searched_user_row, null);
            TextView userNameLabel = row.findViewById(R.id.searchedUserNameLabel);
            userNameLabel.setText(user.userName);
            ImageView hat = row.findViewById(R.id.searchedUserHat);
            hat.setImageResource(CharacterSets.hats.get(user.character.hat));
            ImageView head = row.findViewById(R.id.searchedUserHead);
            head.setImageResource(CharacterSets.heads.get(user.character.head));
            container.addView(row);
        }
    }
}