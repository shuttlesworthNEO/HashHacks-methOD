package com.method.hashhacks.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.method.hashhacks.CustomIncomingTextMessageViewHolder;
import com.method.hashhacks.R;
import com.method.hashhacks.api.MessageApi;
import com.method.hashhacks.models.Author;
import com.method.hashhacks.models.Message;
import com.method.hashhacks.models.Result;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piyush0 on 12/04/17.
 */

public class ChatFragment extends Fragment {

    public static final String TAG = "ChatFrag";

    String senderId;
    String botId;
    MessagesList messagesList;
    MessageInput inputView;
    MessagesListAdapter<Message> adapter;
    IUser user;
    IUser bot;
    private String title;
    private int page;

    public static ChatFragment newInstance(int page, String title) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        chatFragment.setArguments(args);
        return chatFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        initChat(view);
        getGreetingMessages(view);
        return view;
    }

    public void initChat(View view) {

        senderId = "Test";
        botId = "botId";
        user = new Author("avatar", senderId, "Piyush");
        bot = new Author("botA", botId, "Your Assistant");
        messagesList = (MessagesList) view.findViewById(R.id.messagesList);

        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(getContext()).load(url).into(imageView);
            }
        };
        MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();

        holdersConfig.setOutcomingLayout(R.layout.item_custom_outcoming_message);
        holdersConfig.setIncoming(CustomIncomingTextMessageViewHolder.class,
                R.layout.item_custom_incoming_message);

        adapter = new MessagesListAdapter<>(senderId, holdersConfig, imageLoader);
        messagesList.setAdapter(adapter);
        inputView = (MessageInput) view.findViewById(R.id.input);

        inputListener(inputView);
        loadMoreListener(adapter);
        messageClickListener(adapter);

    }

    private void messageClickListener(MessagesListAdapter<Message> adapter) {
        adapter.setOnMessageClickListener(new MessagesListAdapter.OnMessageClickListener<Message>() {
            @Override
            public void onMessageClick(Message message) {

            }
        });
    }

    private void loadMoreListener(MessagesListAdapter<Message> adapter) {
        adapter.setLoadMoreListener(new MessagesListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

            }
        });
    }

    private void inputListener(MessageInput inputView) {
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {

                if (input.length() == 0) {
                    return false;
                }
                Message message = new Message(new Date(), senderId, null, null, input.toString(), user);
                adapter.addToStart(message, true);
                sendToBackend(message);
                return true;
            }
        });
    }

    private void sendToBackend(Message message) {
        Log.d(TAG, "sendToBackend: " + message.getText());
        String url = "http://144dd5c2.ngrok.io/";
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build();
        MessageApi messageApi = retrofit.create(MessageApi.class);

        messageApi.getResult(message.getText()).enqueue(new Callback<ArrayList<Result>>() {
            @Override
            public void onResponse(Call<ArrayList<Result>> call, Response<ArrayList<Result>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                ArrayList<Result> results = response.body();
                Log.d(TAG, "onResponse: " + results);
                Result result = results.get(0);
                Message reply = new Message(new Date(), botId, null, result, result.getUrl(), bot);
                adapter.addToStart(reply, true);
            }

            @Override
            public void onFailure(Call<ArrayList<Result>> call, Throwable t) {

            }
        });
    }

    private void getGreetingMessages(View view) {
    }
}
