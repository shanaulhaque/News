package com.sh.news.list_handler;

import android.app.Activity;
import android.content.Context;

import com.sh.news.model.News;

import java.util.List;

/**
 * Created by shanaulhaque on 12/09/17.
 *
 * class created to act as a delegate
 */

public interface IAdapter {

    List<News> getNewsList();

    void addFav(Integer id);
    void removeFav(Integer id);
    Context getContext();

    Activity getActivity();



}
