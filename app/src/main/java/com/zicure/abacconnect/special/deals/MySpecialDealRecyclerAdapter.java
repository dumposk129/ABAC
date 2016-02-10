package com.zicure.abacconnect.special.deals;

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
import com.zicure.abacconnect.news.News;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DUMP129 on 9/25/2015.
 */
public class MySpecialDealRecyclerAdapter extends RecyclerView.Adapter<MySpecialDealRecyclerAdapter.ViewHolder> implements DataLayerListener, ClickListener {
    private Context mContext;
    private List<SpecialDeals> dealsList = null;
    private ClickListener clickListener = null;
    private DataLayer dataLayer = null;
    private RecyclerView recyclerViewDeals = null;
    private MySpecialDealRecyclerAdapter mySpecialDealRecyclerAdapter = null;
    private int position;

    public void setMySpecialDealListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public MySpecialDealRecyclerAdapter(Context context, List<SpecialDeals> dealsList) {
        this.mContext = context;
        this.dealsList = dealsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_special_deals_recycler_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.specialDeals = dealsList.get(position);

        if (dealsList.get(position).deal_name == null) {
            holder.tvSpecialDealName.setText("");
        } else {
            holder.tvSpecialDealName.setText(dealsList.get(position).deal_name);
        }

        if (dealsList.get(position).deal_discount == null) {
            holder.tvSpecialDealPromotionDetail.setText("");
        } else {
            holder.tvSpecialDealPromotionDetail.setText(dealsList.get(position).deal_discount);
        }

        String convert = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date tmpDate = simpleDateFormat.parse(dealsList.get(position).deal_expiry_date);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm");
            convert = outputDateFormat.format(tmpDate);
            holder.tvSpecialDealExp.setText(convert);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dealsList.get(position).deal_path == null) {

        } else {
            String imgUrl = ApiConfig.IMG_URL + dealsList.get(position).deal_path;
            Glide.with(ApplicationContext.getInstance().getContext())
                    .load(imgUrl)
                    .into(holder.imgViewSpecialDealPromotion);
        }
    }

    @Override
    public int getItemCount() {
        return dealsList.size();
    }

    @Override
    public void addMagazine(List<Magazine> magazineList) {}

    @Override
    public void addYears(List<String> years) {}

    @Override
    public void addViewCounts(String viewCount) {}

    @Override
    public void fetchNews(List<News> newsList) {}

    @Override
    public void fetchSpecialDeals(List<SpecialDeals> specialDealList) {
        mySpecialDealRecyclerAdapter = new MySpecialDealRecyclerAdapter(ApplicationContext.getInstance().getContext(), specialDealList);
        mySpecialDealRecyclerAdapter.setMySpecialDealListener(this);
        recyclerViewDeals.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext()));
        recyclerViewDeals.setAdapter(mySpecialDealRecyclerAdapter);
    }

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
    public void onNewsClickListener(View v, List<News> listNewses, int position) {}

    @Override
    public void onSpecialDealsClickListener(View v, List<SpecialDeals> listDeals, int position) {
        if (clickListener != null) {
            clickListener.onSpecialDealsClickListener(v, listDeals, position);
        }
    }

    @Override
    public void firstDataListener(int position, List<Magazine> magazineList) {}

    @Override
    public void onBusinessClickListener(View v, List<BusinessConnections> listBusiness, int position) {}

    @Override
    public void onJobsClickListener(View v, List<Jobs> listJobs, int position) {}

    public void loadSearch(String searchString) {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.searchDeals(searchString);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgViewSpecialDealPromotion;
        public TextView tvSpecialDealName, tvSpecialDealPromotionDetail, tvSpecialDealExp;
        public SpecialDeals specialDeals;

        public ViewHolder(View itemView) {
            super(itemView);

            imgViewSpecialDealPromotion = (ImageView) itemView.findViewById(R.id.imgViewSpecialDealPromotion);
            tvSpecialDealName = (TextView) itemView.findViewById(R.id.tvSpecialDealName);
            tvSpecialDealPromotionDetail = (TextView) itemView.findViewById(R.id.tvSpecialDealPromotionDetail);
            tvSpecialDealExp = (TextView) itemView.findViewById(R.id.tvSpecialDealExp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        position = getAdapterPosition();
                        clickListener.onSpecialDealsClickListener(v, dealsList, position);
                    }
                }
            });
        }
    }

    public void loadDeals(int userId) {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.getDeals(userId);
    }

    public void sendRecyclerView(RecyclerView recyclerViewDeals) {
        this.recyclerViewDeals = recyclerViewDeals;
    }
}