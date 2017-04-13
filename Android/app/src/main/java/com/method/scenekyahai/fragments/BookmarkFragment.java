package com.method.scenekyahai.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.method.scenekyahai.models.BookmarkItem;
import com.method.scenekyahai.models.DBObject;
import com.method.scenekyahai.models.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import com.method.scenekyahai.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by piyush0 on 13/04/17.
 */

public class BookmarkFragment extends Fragment{

    public static final String TAG = "BookFrag";

    private RecyclerView rvBookmarks;
    private String title;
    private int page;
    private ArrayList<BookmarkItem> items;

    public static BookmarkFragment newInstance(int page, String title) {
        BookmarkFragment bookmarkFragment = new BookmarkFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        bookmarkFragment.setArguments(args);
        return bookmarkFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        items = new ArrayList<>();
        initViews(view);

        getItemsFromDb();
        return view;
    }

    private void initViews(View view) {
        rvBookmarks = (RecyclerView) view.findViewById(R.id.rv_bookmarks);

        rvBookmarks.setAdapter(new MyAdapter());
        rvBookmarks.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getItemsFromDb() {


        if (items == null) {
            items = new ArrayList<>();
        }

        Realm.init(getContext());
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<DBObject> query = realm.where(DBObject.class);

        RealmResults<DBObject> results = query.findAll();
        Log.d("BF", "getItemsFromDb: " + results);
        Gson gson = new Gson();

        for (int i = 0; i < results.size(); i++) {
            Message m = gson.fromJson(results.get(i).getMessageJSON(), Message.class);
            BookmarkItem bi = new BookmarkItem(m.getResult().getImageURL(), m.getResult().getSource(), m.getResult().getTitle(), m.getResult().getUrl());
            items.add(bi);
        }


    }



    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvSource, tvUrl;
        ImageView ivImage;
        Button ib;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = li.inflate(R.layout.item_bookmark, parent, false);

            MyViewHolder viewHolder = new MyViewHolder(view);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.item_bookmark_tv_title);
            viewHolder.tvSource = (TextView) view.findViewById(R.id.item_bookmark_tv_source);
            viewHolder.tvUrl = (TextView) view.findViewById(R.id.item_bookmark_tv_url);
            viewHolder.ivImage = (ImageView) view.findViewById(R.id.item_bookmark_iv_image);
            viewHolder.ib = (Button) view.findViewById(R.id.item_bookmark_btn_copy_url);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tvTitle.setText(items.get(position).getTitle());
            holder.tvUrl.setText(items.get(position).getUrl());
            holder.tvSource.setText(items.get(position).getSource());
            Picasso.with(getContext()).load(items.get(position).getImageURL()).
                    resize(200, 200).
                    into(holder.ivImage);

            holder.ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveToClipboard(items.get(position).getUrl(),getContext());
                }
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public String saveToClipboard(String text, Context context) {
        ClipData myClip;

        myClip = ClipData.newPlainText("text", text);
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(context, "Link copied to clipboard", Toast.LENGTH_SHORT).show();
        return text;
    }
}
