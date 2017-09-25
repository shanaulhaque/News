package com.sh.news.main_page_fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sh.news.IAsyncTask;
import com.sh.news.R;
import com.sh.news.list_handler.IAdapter;
import com.sh.news.list_handler.NewsAdapter;
import com.sh.news.model.AppModel;
import com.sh.news.model.News;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favorites extends Fragment implements IAsyncTask,IAdapter {

    private static final String TAG = Favorites.class.getSimpleName();

    private View mView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mNewsAdapter;
    private AppModel mAppModel;


    public Favorites() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favorites, container, false);


        mAppModel = AppModel.getInstance(getActivity());

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.news_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);

        setHasOptionsMenu(true);

        Log.i(TAG, "fav size" + getNewsList().size());


        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i(TAG, "fav size s " + getNewsList().size());
    }

    @Override
    public void postExecute() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mNewsAdapter != null) {
            Log.i(TAG,"setUserVisibleHint");
            mAppModel.getFavoritesList();
            mNewsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public List<News> getNewsList() {
        return mAppModel.getFavorites();
    }

    @Override
    public void addFav(Integer id) {
        mAppModel.addFav(id);
        mAppModel.getFavoritesList();
        mNewsAdapter.notifyDataSetChanged();

    }

    @Override
    public void removeFav(Integer id) {
        mAppModel.removeFav(id);
        mAppModel.getFavoritesList();
        mNewsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String sort[] = {"Latest to oldest","Oldest to Latest"};

        switch (item.getItemId()) {
            case R.id.action_sort:
                // Toast.makeText(this, "sort selected", Toast.LENGTH_SHORT)
                //       .show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sort")
                        .setItems(sort, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    Collections.sort(mAppModel.getFavorites(), new Comparator<News>() {
                                        @Override
                                        public int compare(News o1, News o2) {
                                            return Long.compare(o1.getTimestamp(), o2.getTimestamp());
                                        }
                                    });

                                }else{
                                    Collections.sort(mAppModel.getFavorites(), new Comparator<News>() {
                                        @Override
                                        public int compare(News o1, News o2) {
                                            return Long.compare(o2.getTimestamp(), o1.getTimestamp());
                                        }
                                    });
                                }
                                mNewsAdapter.notifyDataSetChanged();
                            }
                        });
                builder.show();
                break;
            default:
                break;
        }

        return true;
    }


}
