package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.AddFriendOBJ;
import com.azorom.chatgame.Requests.User.ChatCreatedResponse;
import com.azorom.chatgame.Requests.User.FilteredUser;
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
            RequestResponse<FilteredUser[], HttpRequestError> resp = userReqHandler.searchUser(userName);
            if(resp.error != null){
                Log.d("DEBUG", resp.error.message);
            }else if(resp.response != null){
                fillUserRows(resp.response);
            }
        }else{
            fillUserRows(new FilteredUser[]{});
        }
    }

    private void fillUserRows(FilteredUser[] users){
        LinearLayout container = findViewById(R.id.SearchedUsersRows);
        container.removeAllViews();
        for(FilteredUser user: users){
            View row = View.inflate(this, R.layout.searched_user_row, null);
            TextView userNameLabel = row.findViewById(R.id.searchedUserNameLabel);
            userNameLabel.setText(user.userName);
            ImageView hat = row.findViewById(R.id.searchedUserHat);
            hat.setImageResource(CharacterSets.hats.get(user.character.hat));
            ImageView head = row.findViewById(R.id.searchedUserHead);
            head.setImageResource(CharacterSets.heads.get(user.character.head));
            row.setOnClickListener(v -> this.addFriend(user._id));
            container.addView(row);
        }
    }

    private void addFriend(String friendId){
        RequestResponse<ChatCreatedResponse, HttpRequestError> resp =
                this.userReqHandler.addFriend(new AddFriendOBJ(friendId));
        if(resp.error != null){
            //both users might be friends
            Log.d("DEBUG", resp.error.message);
            if(resp.error.message == "already friends"){
                //get their chat id and send user to it;
            }
        }else if(resp.response != null){
            //after adding both users to each other's frinds list
            //get their newly created chat's id to send current user to the chat page
            sendToChatWithFriend(resp.response.chatId);
        }
    }

    private void sendToChatWithFriend(String chatId){
        Intent i = new Intent(this.getApplicationContext(), ChatPage.class);
        i.putExtra("chatId", chatId);
        startActivity(i);
        this.finish();
    }
}