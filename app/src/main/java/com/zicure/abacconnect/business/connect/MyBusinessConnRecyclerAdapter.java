package com.zicure.abacconnect.business.connect;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.R;
import com.zicure.abacconnect.alumni.search.Alumni;
import com.zicure.abacconnect.api.DataLayer;
import com.zicure.abacconnect.api.DataLayerListener;
import com.zicure.abacconnect.api.ClickListener;
import com.zicure.abacconnect.jobs.Jobss;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.my.deal.Deals;
import com.zicure.abacconnect.news.Newses;
import com.zicure.abacconnect.special.deals.SpecialDeals;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DUMP129 on 9/28/2015.
 */
public class MyBusinessConnRecyclerAdapter extends RecyclerView.Adapter<MyBusinessConnRecyclerAdapter.ViewHolder> implements DataLayerListener, ClickListener {
    private Context mContext;
    private List<BusinessConnections> businessConnectionsList = null;
    private DataLayer dataLayer = null;
    private RecyclerView recyclerViewBusiness = null;
    private ClickListener clickListener = null;
    private int position;
    private MyBusinessConnRecyclerAdapter myBusinessConnRecyclerAdapter = null;

    public void setMyBusinessConnListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public MyBusinessConnRecyclerAdapter(Context context, List<BusinessConnections> businessConnectionsList) {
        this.businessConnectionsList = businessConnectionsList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_connect_recycler_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.businessConnections = businessConnectionsList.get(position);

        holder.tvBusinessCompanyName.setText(businessConnectionsList.get(position).company_name);
        holder.tvBusinessPositionName.setText(businessConnectionsList.get(position).long_description);

        String businessAddress = businessConnectionsList.get(position).province_name_eng;
        holder.tvBusinessAddress.setText(businessAddress);

        String convert = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateCreate = businessConnectionsList.get(position).created;
            if (dateCreate != null) {
                Date tmpDate = simpleDateFormat.parse(dateCreate);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("d MMM yyyy");
                convert = outputDateFormat.format(tmpDate);
                holder.tvBusinessDate.setText(convert);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return businessConnectionsList.size();
    }

    @Override
    public void addMagazine(List<Magazine> magazineList) {
    }

    @Override
    public void addYears(List<String> years) {
    }

    @Override
    public void addViewCounts(String viewCount) {
    }

    @Override
    public void fetchNews(List<Newses> newsesList) {
    }

    @Override
    public void fetchSpecialDeals(List<SpecialDeals> specialDealList) {
    }

    @Override
    public void fetchBusiness(List<BusinessConnections> businessConnectionsList) {
        myBusinessConnRecyclerAdapter = new MyBusinessConnRecyclerAdapter(ApplicationContext.getInstance().getContext(), businessConnectionsList);
        myBusinessConnRecyclerAdapter.setMyBusinessConnListener(this);
        recyclerViewBusiness.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext()));
        recyclerViewBusiness.setAdapter(myBusinessConnRecyclerAdapter);
    }

    @Override
    public void fetchAlumni(List<Alumni> usersList) {
    }

    @Override
    public void fetchJobs(List<Jobss> jobssList) {
    }

    @Override
    public void fetchMyDeal(List<Deals> dealsList) {
    }

    @Override
    public void fetchMyWorkProfile(List<WorkProfile> workProfileList) {
    }

    @Override
    public void fetchMyBusiness(List<MyBusiness> myBusinessList) {
    }

    @Override
    public void onClickInMagazineListener(View v, int position, List<Magazine> listMagazines) {
    }

    @Override
    public void onNewsClickListener(View v, List<Newses> listNewses, int position) {
    }

    @Override
    public void onSpecialDealsClickListener(View v, List<SpecialDeals> listDeals, int position) {
    }

    @Override
    public void firstDataListener(int position, List<Magazine> magazineList) {
    }

    @Override
    public void onBusinessClickListener(View v, List<BusinessConnections> listBusiness, int position) {
        if (clickListener != null) {
            clickListener.onBusinessClickListener(v, listBusiness, position);
        }
    }

    @Override
    public void onJobsClickListener(View v, List<Jobss> listJobs, int position) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBusinessCompanyName, tvBusinessPositionName, tvBusinessAddress, tvBusinessDate;
        public BusinessConnections businessConnections;

        public ViewHolder(View itemView) {
            super(itemView);

            tvBusinessCompanyName = (TextView) itemView.findViewById(R.id.tvBusinessCompanyName);
            tvBusinessPositionName = (TextView) itemView.findViewById(R.id.tvBusinessPositionName);
            tvBusinessAddress = (TextView) itemView.findViewById(R.id.tvBusinessAddress);
            tvBusinessDate = (TextView) itemView.findViewById(R.id.tvBusinessDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        position = getAdapterPosition();
                        clickListener.onBusinessClickListener(v, businessConnectionsList, position);
                    }
                }
            });
        }
    }

    public void loadBusiness() {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.getBusiness();
    }

    public void loadSearch(String searchText) {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.searchBusiness(searchText);
    }

    public void sendRecyclerView(RecyclerView recyclerViewBusiness) {
        this.recyclerViewBusiness = recyclerViewBusiness;
    }
}