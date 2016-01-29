package com.zicure.abacconnect.my.deal;

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
import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.jobs.Jobss;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.news.Newses;
import com.zicure.abacconnect.special.deals.SpecialDeals;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DUMP129 on 9/29/2015.
 */
public class MyDealsRecyclerAdapter extends RecyclerView.Adapter<MyDealsRecyclerAdapter.ViewHolder> implements DataLayerListener {
    private Context mContext;
    private List<Deals> dealsList = null;
    private DataLayer dataLayer = null;
    private RecyclerView recyclerViewMyDeal = null;
    private MyDealsRecyclerAdapter myDealsRecyclerAdapter;

    public MyDealsRecyclerAdapter(Context context, List<Deals> dealsList) {
        this.mContext = context;
        this.dealsList = dealsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_deals_recycler_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.deals = dealsList.get(position);

        holder.tvMyDealStoreName.setText(dealsList.get(position).deal_name);
        holder.tvMyDealDiscount.setText(dealsList.get(position).deal_discount);

        String convert = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateDealExp = dealsList.get(position).deal_expiry_date;
            if (dateDealExp != null) {
                Date tmpDate = simpleDateFormat.parse(dateDealExp);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm");
                convert = outputDateFormat.format(tmpDate);
                holder.tvMyDealUsedOn.setText(convert);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String imgUrl = ApiConfig.IMG_URL + dealsList.get(position).deal_path;
        Glide.with(ApplicationContext.getInstance().getContext())
                .load(imgUrl)
                .into(holder.imgViewMyDealPromotion);
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
    public void fetchNews(List<Newses> newsesList) {}

    @Override
    public void fetchSpecialDeals(List<SpecialDeals> specialDealList) {}

    @Override
    public void fetchBusiness(List<BusinessConnections> businessConnectionsList) {}

    @Override
    public void fetchAlumni(List<Alumni> usersList) {}

    @Override
    public void fetchJobs(List<Jobss> jobssList) {}

    @Override
    public void fetchMyDeal(List<Deals> dealsList) {
        myDealsRecyclerAdapter = new MyDealsRecyclerAdapter(ApplicationContext.getInstance().getContext(), dealsList);
        recyclerViewMyDeal.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext()));
        recyclerViewMyDeal.setAdapter(myDealsRecyclerAdapter);
    }

    @Override
    public void fetchMyWorkProfile(List<WorkProfile> workProfileList) {}

    @Override
    public void fetchMyBusiness(List<MyBusiness> myBusinessList) {}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgViewMyDealPromotion;
        public TextView tvMyDealStoreName, tvMyDealDiscount, tvMyDealUsedOn;
        public Deals deals;

        public ViewHolder(View itemView) {
            super(itemView);

            imgViewMyDealPromotion = (ImageView) itemView.findViewById(R.id.imgViewMyDealPromotion);
            tvMyDealStoreName = (TextView) itemView.findViewById(R.id.tvMyDealStoreName);
            tvMyDealDiscount = (TextView) itemView.findViewById(R.id.tvMyDealDiscount);
            tvMyDealUsedOn = (TextView) itemView.findViewById(R.id.tvMyDealUsedOn);
        }
    }

    public void loadMyDeal(int userId) {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.getMyDeal(userId);
    }

    public void sendRecyclerView(RecyclerView recyclerViewMyDeal) {
        this.recyclerViewMyDeal = recyclerViewMyDeal;
    }
}