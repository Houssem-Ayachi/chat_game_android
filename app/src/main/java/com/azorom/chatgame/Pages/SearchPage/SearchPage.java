package com.azorom.chatgame.Pages.SearchPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.azorom.chatgame.Pages.ChatPage.ChatPage;
import com.azorom.chatgame.R;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.AddFriendOBJ;
import com.azorom.chatgame.Requests.User.ChatCreatedResponse;
import com.azorom.chatgame.Requests.User.FilteredUser;
import com.azorom.chatgame.Requests.User.UserRequests;

public class SearchPage extends AppCompatActivity {

    UserRequests userReqHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        userReqHandler = new UserRequests(this.getApplicationContext());

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
                displayError(resp.error.message);
            }else if(resp.response != null){
                fillUserRows(resp.response);
            }
        }else{
            fillUserRows(new FilteredUser[]{});
        }
    }

    private void fillUserRows(FilteredUser[] users){
        ListView container = findViewById(R.id.container);
        SearchedUsersAdapter adapter = new SearchedUsersAdapter(this, users);
        adapter.setOnSearchedUserClickListener(user -> addFriend(user._id));
        container.setAdapter(adapter);
    }

    private void addFriend(String friendId){
        RequestResponse<ChatCreatedResponse, HttpRequestError> resp = this.userReqHandler.addFriend(new AddFriendOBJ(friendId));
        if(resp.error != null){
            //both users might be friends
            displayError(resp.error.message);
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

    private void displayError(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}