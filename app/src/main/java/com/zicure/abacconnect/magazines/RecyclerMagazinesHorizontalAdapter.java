package com.zicure.abacconnect.magazines;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zicure.abacconnect.alumni.search.Alumni;

import com.zicure.abacconnect.api.ClickListener;
import com.zicure.abacconnect.R;
import com.zicure.abacconnect.api.DataLayerListener;
import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.api.DataLayer;
import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.jobs.Jobs;
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.my.deal.Deals;
import com.zicure.abacconnect.news.News;
import com.zicure.abacconnect.special.deals.SpecialDeals;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.util.List;

/**
 * Created by DUMP129 on 10/2/2015.
 */
public class RecyclerMagazinesHorizontalAdapter extends RecyclerView.Adapter<RecyclerMagazinesHorizontalAdapter.ViewHolder> implements DataLayerListener, ClickListener {
    private String yearStr = null;
    private Context context;
    private List<Magazine> magazineList = null;
    private DataLayer dataLayer = null;
    private RecyclerView horizontalRV = null;
    private ClickListener clickListener = null;
    private int position;

    public void setFirstDataListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnImageClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RecyclerMagazinesHorizontalAdapter(Context context, String yearStr) {
        this.context = context;
        this.yearStr = yearStr;
    }

    public RecyclerMagazinesHorizontalAdapter(Context context1, List<Magazine> magazineList) {
        this.context = context1;
        this.magazineList = magazineList;
    }

    public RecyclerMagazinesHorizontalAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_magazine_info_recycler_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.magazine = magazineList.get(position);

        // Set Magazine Monthly
        if (magazineList.get(position).magazine_intro == null) {
            holder.tvMagazineIntro.setText("");
        } else {
            holder.tvMagazineIntro.setText(magazineList.get(position).magazine_intro);
        }

        if (magazineList.get(position).magazine_thumbnail == null) {

        } else {
            // Set Cover Magazine Monthly
            Glide.with(ApplicationContext.getInstance().getContext())
                    .load(magazineList.get(position).magazine_thumbnail)
                    .centerCrop()
                    .into(holder.imgViewCoverMagazine);

        }

        if (magazineList != null) {
            clickListener.firstDataListener(position, magazineList);
        }
    }

    @Override
    public int getItemCount() {
        return magazineList.size();
    }

    @Override
    public void addMagazine(List<Magazine> magazineList) {
        RecyclerMagazinesHorizontalAdapter recyclerMagazinesHorizontalAdapter = new RecyclerMagazinesHorizontalAdapter(ApplicationContext.getInstance().getContext(), magazineList);
        recyclerMagazinesHorizontalAdapter.setOnImageClickListener(this);
        horizontalRV.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext(), LinearLayoutManager.HORIZONTAL, false));
        horizontalRV.setAdapter(recyclerMagazinesHorizontalAdapter);
    }

    @Override
    public void addYears(List<String> years) {}

    @Override
    public void addViewCounts(String viewCount) {}

    @Override
    public void fetchNews(List<News> newsList) {}

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
    public void onClickInMagazineListener(View v, int position, List<Magazine> listMagazines) {
        if (clickListener != null) {
            clickListener.onClickInMagazineListener(v, position, listMagazines);
        }
    }

    @Override
    public void onNewsClickListener(View v, List<News> listNewses, int position) {}

    @Override
    public void onSpecialDealsClickListener(View v, List<SpecialDeals> listDeals, int position) {}

    @Override
    public void firstDataListener(int position, List<Magazine> magazineList) {
        if (clickListener != null) {
            clickListener.firstDataListener(position, magazineList);
        }
    }

    @Override
    public void onBusinessClickListener(View v, List<BusinessConnections> listBusiness, int position) {}

    @Override
    public void onJobsClickListener(View v, List<Jobs> listJobs, int position) {}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgViewCoverMagazine = null;
        public TextView tvMagazineIntro = null;
        public Magazine magazine = null;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMagazineIntro = (TextView) itemView.findViewById(R.id.tvMagazineIntro);
            imgViewCoverMagazine = (ImageView) itemView.findViewById(R.id.imgViewCoverMagazine);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        position = getAdapterPosition();
                        clickListener.onClickInMagazineListener(v, position, magazineList);
                    }
                }
            });
        }
    }

    public void loadMagazine() {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.loadMagazines(yearStr);
    }

    public void sendHorizontalRV(RecyclerView horizontalRV) {
        this.horizontalRV = horizontalRV;
    }
}