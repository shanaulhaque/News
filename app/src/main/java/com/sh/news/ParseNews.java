package com.sh.news;

import android.app.Activity;
import android.util.Log;

import com.sh.news.model.AppModel;
import com.sh.news.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shanaulhaque on 12/09/17.
 */

public class ParseNews {

    public static final String TAG = ParseNews.class.getSimpleName();
    public void parse(String jsonStr, Activity activity){
        AppModel appModel = AppModel.getInstance(activity);
        appModel.getNewsList().clear();
        if (jsonStr != null) {
            try {

                JSONArray newsJsonArray = new JSONArray(jsonStr);

                for (int i = 0; i < newsJsonArray.length(); i++) {
                    JSONObject news_json = newsJsonArray.getJSONObject(i);

                    Integer id = news_json.optInt("ID");
                    String title = news_json.optString("TITLE");
                    String url = news_json.optString("URL");
                    String publisher = news_json.optString("PUBLISHER");
                    Character category = news_json.optString("CATEGORY").charAt(0);
                    String hostname = news_json.optString("HOSTNAME");
                    Long timestamp = news_json.optLong("TIMESTAMP");

                    News news = new News(id,title,url,publisher,category,hostname,timestamp);

                    appModel.getNewsList().add(news);

                    if(appModel.getFavSet().contains(id.toString())){
                        news.setFav(true);
                    }

                }
                Log.d(TAG, "length: " + appModel.getNewsList().size());
                Log.d(TAG, "fav length: " + appModel.getFavorites().size());
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");

        }
    }



}

