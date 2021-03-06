package com.zicure.abacconnect.business.connect;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.R;
import com.zicure.abacconnect.alumni.search.Alumni;
import com.zicure.abacconnect.api.ApiConfig;
import com.zicure.abacconnect.api.DataLayer;
import com.zicure.abacconnect.api.DataLayerListener;
import com.zicure.abacconnect.jobs.Jobs;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.my.deal.Deals;
import com.zicure.abacconnect.news.News;
import com.zicure.abacconnect.special.deals.SpecialDeals;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.util.List;

/**
 * Created by DUMP129 on 10/27/2015.
 */
public class BusinessViewImgRecyclerAdapter extends RecyclerView.Adapter<BusinessViewImgRecyclerAdapter.ViewHolder> implements DataLayerListener {
    private Context mContext;
    private List<BusinessConnections> businessConnectionsList = null;
    private DataLayer dataLayer = null;
    private RecyclerView recyclerViewBusiness = null;
    private BusinessViewImgRecyclerAdapter businessViewImgRecyclerAdapter = null;

    public BusinessViewImgRecyclerAdapter(Context context, List<BusinessConnections> businessConnectionsList) {
        this.businessConnectionsList = businessConnectionsList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_business_view_img_recycler_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.businessConnections = businessConnectionsList.get(position);

        if (businessConnectionsList.get(position).business_thumbnail == null) {
        } else {
            String imgUrl = ApiConfig.IMG_URL + businessConnectionsList.get(position).business_thumbnail;
            Glide.with(ApplicationContext.getInstance().getContext())
                    .load(imgUrl).centerCrop().into(holder.rvImgViewBusinessView);
        }
    }

    @Override
    public int getItemCount() {
        return businessConnectionsList.size();
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
    public void fetchSpecialDeals(List<SpecialDeals> specialDealList) {}

    @Override
    public void fetchBusiness(List<BusinessConnections> businessConnectionsList) {
        businessViewImgRecyclerAdapter = new BusinessViewImgRecyclerAdapter(ApplicationContext.getInstance().getContext(), businessConnectionsList);
        recyclerViewBusiness.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBusiness.setAdapter(businessViewImgRecyclerAdapter);
    }

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView rvImgViewBusinessView;
        public BusinessConnections businessConnections;

        public ViewHolder(View itemView) {
            super(itemView);

            rvImgViewBusinessView = (ImageView) itemView.findViewById(R.id.rvImgViewBusinessView);
        }
    }

    public void loadImage() {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.getBusiness();
    }

    public void sendRecyclerView(RecyclerView recyclerViewBusiness) {
        this.recyclerViewBusiness = recyclerViewBusiness;
    }
}
