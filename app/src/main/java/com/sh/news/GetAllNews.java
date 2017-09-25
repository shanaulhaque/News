package com.sh.news;

import android.os.AsyncTask;
import android.util.Log;

import com.sh.news.net.NetworkIO;

/**
 * Created by shanaulhaque on 12/09/17.
 */

public class GetAllNews extends AsyncTask<String, Void, Void> {

    private static final String TAG = GetAllNews.class.getSimpleName();

    private IAsyncTask iAsyncTask;

    public GetAllNews(IAsyncTask iAsyncTask) {
        this.iAsyncTask = iAsyncTask;
    }

    @Override
    protected Void doInBackground(String... url) {
        NetworkIO sh = new NetworkIO();


        String jsonStr = sh.makeServiceCall(url[0]);

       // Log.d(TAG, "Response from url: " + jsonStr);

        ParseNews parseNews = new ParseNews();
        parseNews.parse(jsonStr,iAsyncTask.getActivity());

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        iAsyncTask.postExecute();
    }
}
