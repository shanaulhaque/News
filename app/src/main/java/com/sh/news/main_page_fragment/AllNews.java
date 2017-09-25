package com.sh.news.main_page_fragment;


import android.app.ProgressDialog;
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

import com.sh.news.GetAllNews;
import com.sh.news.IAsyncTask;
import com.sh.news.R;
import com.sh.news.list_handler.IAdapter;
import com.sh.news.list_handler.IListListener;
import com.sh.news.list_handler.NewsAdapter;
import com.sh.news.list_handler.NewsRVListener;
import com.sh.news.model.AppModel;
import com.sh.news.model.News;
import com.sh.news.utils.Const;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllNews extends Fragment implements IAsyncTask, IListListener, IAdapter {

    private static final String TAG = AllNews.class.getSimpleName();

    private GetAllNews mGetAllNews;
    private View mView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mNewsAdapter;
    private NewsRVListener newsRVListener;
    private AppModel mAppModel;
    private static Integer pageNumber;
    private final static Integer PAGE_SIZE = 10;
    private ProgressDialog dialog;

    public AllNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_all_news, container, false);

        pageNumber = 0;

        mAppModel = AppModel.getInstance(getActivity());

        String url = String.format(Const.newsUrl);
        mGetAllNews = new GetAllNews(this);
        mGetAllNews.execute(url);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.news_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);

        dialog = new ProgressDialog(this.getContext());
        dialog.setTitle("Please wait...");
        dialog.setMessage("Fetching news");
        dialog.show();


        /**
         * Added For pagination
         **/
        newsRVListener = new NewsRVListener(this,mLayoutManager);
        mRecyclerView.addOnScrollListener(newsRVListener);

        setHasOptionsMenu(true);


        return mView;
    }

    @Override
    public void postExecute() {
        dialog.dismiss();
        int listSize = mAppModel.getNewsList().size();
        int index = pageNumber * PAGE_SIZE;
        int lastIndex = index + PAGE_SIZE;
        while(index < lastIndex && index < listSize){
            mAppModel.getNewsListToShow().add(mAppModel.getNewsList().get(index));
            index++;
        }
        Log.i(TAG, "List Size " + mAppModel.getNewsListToShow().size());
        mNewsAdapter.notifyDataSetChanged();
        pageNumber++;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mNewsAdapter != null) {
            mNewsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateList() {
        postExecute();
    }

    @Override
    public List<News> getNewsList() {
        return mAppModel.getNewsListToShow();
    }

    @Override
    public void addFav(Integer id) {
        Log.i(TAG,"addFav " + id);
        mAppModel.addFav(id);
    }

    @Override
    public void removeFav(Integer id) {
        Log.i(TAG,"removeFav " + id);
        mAppModel.removeFav(id);
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

                                    Collections.sort(mAppModel.getNewsListToShow(), new Comparator<News>() {
                                        @Override
                                        public int compare(News o1, News o2) {
                                            return Long.compare(o1.getTimestamp(), o2.getTimestamp());
                                        }
                                    });
                                }else{
                                    Collections.sort(mAppModel.getNewsListToShow(), new Comparator<News>() {
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
