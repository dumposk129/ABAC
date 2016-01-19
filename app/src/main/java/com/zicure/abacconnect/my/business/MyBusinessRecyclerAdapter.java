package com.zicure.abacconnect.my.business;

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
import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.jobs.Jobss;
import com.zicure.abacconnect.magazines.Magazine;
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
public class MyBusinessRecyclerAdapter extends RecyclerView.Adapter<MyBusinessRecyclerAdapter.ViewHolder> implements DataLayerListener {
    private List<MyBusiness> myBusinessList;
    private Context mContext;
    private DataLayer dataLayer = null;
    private RecyclerView recyclerViewMyBusiness = null;
    private MyBusinessRecyclerAdapter myBusinessRecyclerAdapter = null;

    public MyBusinessRecyclerAdapter(Context context, List<MyBusiness> myBusinessList) {
        this.myBusinessList = myBusinessList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_business_recycler_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.myBusiness = myBusinessList.get(position);

        holder.tvMyBusinessCompany.setText(myBusinessList.get(position).company_name);

        String myBusinessAddress = myBusinessList.get(position).street_address + " " + myBusinessList.get(position).locality_name_eng
                + " " + myBusinessList.get(position).district_name_eng + " " + myBusinessList.get(position).province_name_eng
                + " " + myBusinessList.get(position).zipcode;
        holder.tvMyBusinessAddress.setText(myBusinessAddress);

        holder.tvMyBusinessProduct.setText(myBusinessList.get(position).long_description);

        String convert = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateCreated = myBusinessList.get(position).created;
            if (dateCreated != null) {
                Date tmpDate = simpleDateFormat.parse(dateCreated);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("d MMM yyyy");
                convert = outputDateFormat.format(tmpDate);
                holder.tvMyBusinessDate.setText(convert);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return myBusinessList.size();
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
        myBusinessRecyclerAdapter = new MyBusinessRecyclerAdapter(ApplicationContext.getInstance().getContext(), myBusinessList);
        recyclerViewMyBusiness.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext()));
        recyclerViewMyBusiness.setAdapter(myBusinessRecyclerAdapter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMyBusinessCompany, tvMyBusinessAddress, tvMyBusinessProduct, tvMyBusinessDate;
        public MyBusiness myBusiness;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMyBusinessCompany = (TextView) itemView.findViewById(R.id.tvMyBusinessCompany);
            tvMyBusinessAddress = (TextView) itemView.findViewById(R.id.tvMyBusinessAddress);
            tvMyBusinessProduct = (TextView) itemView.findViewById(R.id.tvMyBusinessProduct);
            tvMyBusinessDate = (TextView) itemView.findViewById(R.id.tvMyBusinessDate);
        }
    }

    public void loadMyBusiness(int userId) {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.getMyBusiness(userId);
    }

    public void sendRecyclerView(RecyclerView recyclerViewMyBusiness) {
        this.recyclerViewMyBusiness = recyclerViewMyBusiness;
    }
}