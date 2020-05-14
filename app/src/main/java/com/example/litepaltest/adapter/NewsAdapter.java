package com.example.litepaltest.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.litepaltest.R;
import com.example.litepaltest.activity.NewsActivity;
import com.example.litepaltest.entity.News;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHodler> {

    private Context adapterContext;
    private Context context;
    private List<News> newsList;

    static class ViewHodler extends RecyclerView.ViewHolder{
        View newsView;
        CardView cardView;
        ImageView NewsImage;
        TextView NewsTitle;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            newsView = itemView;
            NewsImage = (ImageView) itemView.findViewById(R.id.news_image);
            NewsTitle = (TextView) itemView.findViewById(R.id.news_title_main);
        }
    }



    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_news_item,parent,false);
        //给recyclerView的每个item添加点击事件
        final ViewHodler holder = new ViewHodler(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                News news = newsList.get(positon);
                Toast.makeText(v.getContext(),"click"+ news.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(adapterContext , NewsActivity.class);
                intent.putExtra("newsTitle", news.getTitle() );
                intent.putExtra("newsContent",news.getContent());
                intent.putExtra("newsPitctureUrl",news.getPrictureUrl());
                Log.d(TAG, "onClick: "+ news.getTitle()+news.getPrictureUrl());
                adapterContext.startActivity(intent);


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        News news = newsList.get(position);
        holder.NewsTitle.setText(news.getTitle());
        Glide.with(context).load(R.drawable.news2).into(holder.NewsImage);

    }



    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public NewsAdapter(List<News> newsList,Context context) {
        adapterContext = context;
        this.newsList = newsList;
    }
}
