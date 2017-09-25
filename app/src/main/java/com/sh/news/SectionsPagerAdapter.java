package com.sh.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sh.news.main_page_fragment.AllNews;
import com.sh.news.main_page_fragment.Favorites;

/**
 * Created by shanaulhaque on 12/09/17.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new AllNews();
            case 1: return new Favorites();
            default: return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "TOP STORIES";
            case 1: return "FAVORITES";
            default: return null;

        }
    }

}

