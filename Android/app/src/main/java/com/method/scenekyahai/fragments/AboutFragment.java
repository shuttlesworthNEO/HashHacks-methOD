package com.method.scenekyahai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.method.scenekyahai.R;

import java.util.ArrayList;

/**
 * Created by piyush0 on 13/04/17.
 */

public class AboutFragment extends Fragment {
    public static final String TAG = "AboutFrag";

    private String title;
    private int page;

    public static AboutFragment newInstance(int page, String title) {
        AboutFragment aboutFragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        aboutFragment.setArguments(args);
        return aboutFragment;
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

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        return view;
    }
}
