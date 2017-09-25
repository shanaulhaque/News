package com.sh.news.list_handler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sh.news.Browser;
import com.sh.news.R;
import com.sh.news.model.AppModel;
import com.sh.news.model.News;
import com.sh.news.utils.GetTimeAgo;

import java.util.List;
import java.util.Set;

/**
 * Created by shanaulhaque on 12/09/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private static final String TAG = NewsAdapter.class.getSimpleName();


    private Context context;

    private List<News> newsList;

    private IAdapter iAdapter;

    public NewsAdapter(IAdapter iAdapter) {
        this.iAdapter = iAdapter;
        this.newsList = iAdapter.getNewsList();
        this.context = iAdapter.getContext();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row_list_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final News news  = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.publisher.setText(news.getPublisher());
        GetTimeAgo getTimeAgo = new GetTimeAgo();
        String ago = getTimeAgo.getTimeAgo(news.getTimestamp(),context);

        holder.timestamp.setText(ago);
        if(news.getFav())
            holder.btn_star.setBackgroundResource(R.drawable.ic_star_white_24dp);
        else{
            holder.btn_star.setBackgroundResource(R.drawable.ic_star_border_white_24dp);
        }

        holder.btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!news.getFav()){
                    holder.btn_star.setBackgroundResource(R.drawable.ic_star_white_24dp);
                    iAdapter.addFav(news.getId());
                }else{
                    holder.btn_star.setBackgroundResource(R.drawable.ic_star_border_white_24dp);
                    iAdapter.removeFav(news.getId());
                }
                news.setFav(!news.getFav());
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Browser.class);
                intent.putExtra("URL",news.getUrl());
                intent.putExtra("TITLE",news.getTitle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }




    public class NewsViewHolder extends RecyclerView.ViewHolder{
        public TextView title, publisher,timestamp;
        public RelativeLayout btn_fav;
        public Button btn_star;
        public NewsViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            publisher = (TextView) view.findViewById(R.id.tv_publisher);
            timestamp = (TextView) view.findViewById(R.id.tv_timestamp);
            btn_fav = (RelativeLayout) view.findViewById(R.id.btn_fav);
            btn_star = (Button) view.findViewById(R.id.btn_star);

        }

    }

}
