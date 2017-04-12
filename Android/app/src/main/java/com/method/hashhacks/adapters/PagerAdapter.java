package com.method.hashhacks.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.method.hashhacks.fragments.ChatFragment;

/**
 * Created by piyush0 on 12/04/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 1;
    private Context context;

    public PagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChatFragment.newInstance(0, "Page # 1");

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Chat Bot";
                break;

            default:
                title = "Not found";
        }
        return title;
    }
}
