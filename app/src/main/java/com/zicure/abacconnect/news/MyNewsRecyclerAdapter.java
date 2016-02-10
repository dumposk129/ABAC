package com.zicure.abacconnect.news;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.R;
import com.zicure.abacconnect.alumni.search.Alumni;

import com.zicure.abacconnect.api.ApiConfig;
import com.zicure.abacconnect.api.DataLayer;
import com.zicure.abacconnect.api.DataLayerListener;
import com.zicure.abacconnect.api.ClickListener;
import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.jobs.Jobs;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.my.deal.Deals;
import com.zicure.abacconnect.special.deals.SpecialDeals;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.util.List;

/**
 * Created by DUMP129 on 10/27/2015.
 */
public class MyNewsRecyclerAdapter extends RecyclerView.Adapter<MyNewsRecyclerAdapter.ViewHolder> implements DataLayerListener, ClickListener {
    private Context mContext;
    private List<News> newsList = null;
    private DataLayer dataLayer = null;
    private RecyclerView recyclerViewNews = null;
    private ClickListener clickListener = null;
    private int position;
    private MyNewsRecyclerAdapter myNewsRecyclerAdapter;

    public void setMyNewsListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public MyNewsRecyclerAdapter(Context mContext, List<News> newsList) {
        this.newsList = newsList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_recycler_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.news = newsList.get(position);

        if (newsList.get(position).news_topic == null) {
            holder.tvNewsTitle.setText("");
        } else {
            holder.tvNewsTitle.setText(newsList.get(position).news_topic);
        }

        if (newsList.get(position).news_intro == null) {
            holder.tvNewsDetail.setText("");
        } else {
            holder.tvNewsDetail.setText(newsList.get(position).news_intro);
        }

        if (newsList.get(position).view_count == null) {
            holder.tvNewsView.setText("");
        } else {
            holder.tvNewsView.setText(newsList.get(position).view_count);
        }

        if (newsList.get(position).news_thumbnail == null) {

        } else {
            String imgUrl = ApiConfig.IMG_URL + newsList.get(position).news_thumbnail;
            Glide.with(ApplicationContext.getInstance().getContext())
                    .load(imgUrl)
                    .into(holder.imgViewNews);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void addMagazine(List<Magazine> magazineList) {}

    @Override
    public void addYears(List<String> years) {}

    @Override
    public void addViewCounts(String viewCount) {}

    @Override
    public void fetchNews(List<News> newsList) {
        myNewsRecyclerAdapter = new MyNewsRecyclerAdapter(ApplicationContext.getInstance().getContext(), newsList);
        myNewsRecyclerAdapter.setMyNewsListener(this);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext()));
        recyclerViewNews.setAdapter(myNewsRecyclerAdapter);
    }

    @Override
    public void fetchSpecialDeals(List<SpecialDeals> specialDealList) {}

    @Override
    public void fetchBusiness(List<BusinessConnections> businessConnectionsList) {}

    @Override
    public void fetchAlumni(List<Alumni> usersList) {}

    @Override
    public void fetchJobs(List<Jobs> jobsList) {}

    @Override
    public void fetchMyDeal(List<Deals> dealsList) {}

    @Override
    public void fetchMyWorkProfile(List<WorkProfile> workProfileList) {}

    @Override
    public void fetchMyBusiness(List<MyBusiness> myBusinessList) {}

    @Override
    public void onClickInMagazineListener(View v, int position, List<Magazine> listMagazines) {}

    @Override
    public void onNewsClickListener(View v, List<News> listNewses, int position) {
        if (clickListener != null) {
            clickListener.onNewsClickListener(v, listNewses, position);
        }
    }

    @Override
    public void onSpecialDealsClickListener(View v, List<SpecialDeals> listDeals, int position) {}

    @Override
    public void firstDataListener(int position, List<Magazine> magazineList) {}

    @Override
    public void onBusinessClickListener(View v, List<BusinessConnections> listBusiness, int position) {}

    @Override
    public void onJobsClickListener(View v, List<Jobs> listJobs, int position) {}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgViewNews;
        public TextView tvNewsTitle, tvNewsDetail, tvNewsView;
        public News news;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgViewNews = (ImageView) itemView.findViewById(R.id.imgViewNews);
            tvNewsTitle = (TextView) itemView.findViewById(R.id.tvNewsTitle);
            tvNewsDetail = (TextView) itemView.findViewById(R.id.tvNewsDetail);
            tvNewsView = (TextView) itemView.findViewById(R.id.tvNewsView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        position = getAdapterPosition();
                        clickListener.onNewsClickListener(v, newsList, position);
                    }
                }
            });
        }
    }

    public void loadNews() {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.getNews();
    }

    public void loadSearch(String searchText) {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.searchNews(searchText);
    }

    public void sendRecyclerView(RecyclerView recyclerViewNews) {
        this.recyclerViewNews = recyclerViewNews;
    }
}