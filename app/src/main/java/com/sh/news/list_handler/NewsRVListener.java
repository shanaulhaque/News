package com.sh.news.list_handler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

/**
 * Created by shanaulhaque on 12/09/17.
 *
 * Class used for pagination
 */

public class NewsRVListener extends RecyclerView.OnScrollListener {


    private boolean userScrolled = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    IListListener iListListener;
    LinearLayoutManager mLayoutManager;

    public  NewsRVListener(IListListener iListListener, LinearLayoutManager layoutManager){
        this.iListListener = iListListener;
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView,
                                     int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            userScrolled = true;
        }

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx,
                           int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = mLayoutManager.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();
        pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
        if (userScrolled && (visibleItemCount + pastVisibleItems) == totalItemCount) {
            userScrolled = false;
            iListListener.updateList();
        }

    }

}
