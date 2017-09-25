package com.sh.news.model;

import android.app.Activity;
import android.util.Log;

import com.sh.news.ApplicationBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shanaulhaque on 12/09/17.
 */

public class AppModel {
    private final static String TAG = AppModel.class.getSimpleName();


    private static AppModel appModel;
    private ArrayList<News> newsList = new ArrayList<>();
    /**
     * List use for pagination
     */
    private ArrayList<News> newsListToShow = new ArrayList<>();
    /**
     * List use for favorites
     */
    private ArrayList<News> favorites  = new ArrayList<>();
    /**
     * List use for favorites
     */
    private Set<String>  favSet = new HashSet<>();

    private Activity mActivity;

    public static AppModel getInstance(Activity activity){
        if(appModel == null){
            appModel = new AppModel(activity);
        }
        appModel.mActivity = activity;
        return appModel;
    }

    private AppModel(Activity activity) {

        this.mActivity = activity;
    }


    public void loadConfiguration(){
        newsList.clear();
        newsListToShow.clear();
        favorites.clear();
        favSet.clear();
        /**
         * load favorites when app start
         */
        favSet  = ApplicationBase.getmSharedPreferences().getStringSet("favorites",favSet);

    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    public ArrayList<News> getNewsListToShow() {
        return newsListToShow;
    }

    public void setNewsListToShow(ArrayList<News> newsListToShow) {
        this.newsListToShow = newsListToShow;
    }

    /**
     * save preference before exit
     */
    public void onAppDestroy(){
        Log.i(TAG,"onAppDestroy " + favSet.size());
        ApplicationBase.getEditor().remove("favorites").commit();
        ApplicationBase.getEditor().putStringSet("favorites",favSet).commit();
    }

    public List<News> getFavoritesList() {

        favorites.clear();

        Log.i(TAG, "size " + favSet.size());

        for(News news: newsList) {
            if(favSet.contains(news.getId().toString())){
                favorites.add(news);
            }
        }

        Log.i(TAG, "size " + favorites.size());

        return favorites;
    }

    public ArrayList<News> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<News> favorites) {
        this.favorites = favorites;
    }

    public Set<String> getFavSet() {
        return favSet;
    }

    public void setFavSet(Set<String> favSet) {
        this.favSet = favSet;
    }

    public void addFav(Integer id) {
        favSet.add(id.toString());

    }

    public void removeFav(Integer id) {
        favSet.remove(id.toString());
    }
}
