package com.method.scenekyahai.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.method.scenekyahai.CustomIncomingTextMessageViewHolder;
import com.method.scenekyahai.R;
import com.method.scenekyahai.api.MessageApi;
import com.method.scenekyahai.models.Author;
import com.method.scenekyahai.models.DBObject;
import com.method.scenekyahai.models.Message;
import com.method.scenekyahai.models.Result;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by piyush0 on 12/04/17.
 */

public class ChatFragment extends Fragment {
    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    public static final String TAG = "ChatFrag";
    public static final String FIRST_SHARED_PREFS = "FirstSp";
    private final int REQ_CODE_SPEECH_INPUT = 100;

    String senderId;
    String botId;
    MessagesList messagesList;
    MessageInput inputView;
    MessagesListAdapter<Message> adapter;
    IUser user;
    IUser bot;
    private String title;
    private int page;


    Button speech;

    SharedPreferences sharedPreferences;

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

        sharedPreferences = getContext().getSharedPreferences(FIRST_SHARED_PREFS, MODE_PRIVATE);

        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        initChat(view);

        speech = (Button) view.findViewById(R.id.btn_speech);


        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });


        getGreetingMessages(view);
        return view;
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    "Sorry your device doesn't support speech",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    inputView.getInputEditText().setText(result.get(0));
                }
                break;
            }
            default:


        }
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
                Picasso.with(getContext()).load(url).
                        resize(100, 100).
                        into(imageView);
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
        longClickListener(adapter);
        messageClickListener(adapter);
    }

    private void longClickListener(MessagesListAdapter<Message> adapter) {
        adapter.setOnMessageLongClickListener(new MessagesListAdapter.OnMessageLongClickListener<Message>() {
            @Override
            public void onMessageLongClick(Message message) {
                Realm.init(getContext());
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();

                DBObject dbObject = realm.createObject(DBObject.class);
                Gson gson = new Gson();
                String json = gson.toJson(message);
                dbObject.setMessageJSON(json);
                realm.commitTransaction();

                Toast.makeText(getContext(), "Bookmarked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void messageClickListener(MessagesListAdapter<Message> adapter) {
        adapter.setOnMessageClickListener(new MessagesListAdapter.OnMessageClickListener<Message>() {
            @Override
            public void onMessageClick(Message message) {

                if (message.getResult() != null) {
                    String url = message.getResult().getUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
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
                storeQueryToDb(message);

                return true;
            }
        });
    }

    private void storeQueryToDb(Message message) {
        sharedPreferences.edit().putString("query", message.getText()).commit();
    }


    private void sendToBackend(Message message) {
        Log.d(TAG, "sendToBackend: " + message.getText());
        String url = "http://c0606bad.ngrok.io/";
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build();
        MessageApi messageApi = retrofit.create(MessageApi.class);

        messageApi.getResult(message.getText()).enqueue(new Callback<ArrayList<Result>>() {
            @Override
            public void onResponse(Call<ArrayList<Result>> call, Response<ArrayList<Result>> response) {
                ArrayList<Result> results = response.body();

                for (int i = 0; i < results.size(); i++) {
                    Result result = results.get(i);
                    Message reply = new Message(new Date(), botId, null, result, result.getUrl(), bot);

                    adapter.addToStart(reply, true);
                }

                Message reply = new Message(new Date(), botId, null, null, "What other topics are you interested in ?", bot);
                adapter.addToStart(reply, true);

            }

            @Override
            public void onFailure(Call<ArrayList<Result>> call, Throwable t) {

            }
        });
    }

    private void getGreetingMessages(View view) {


        if (sharedPreferences.getString("query", "").equals("")) {
            // First time
            Message message = new Message(new Date(), botId, null, null, "Welcome to app", bot);//TODO: Randomize
            adapter.addToStart(message, true);
            message = new Message(new Date(), botId, null, null, "What topics are you interested in ? ", bot);
            adapter.addToStart(message, true);
        } else {
            Message message = new Message(new Date(), senderId, null, null, sharedPreferences.getString("query", ""), user);
            sendToBackend(message);
            adapter.addToStart(message, true);
        }

    }
}
