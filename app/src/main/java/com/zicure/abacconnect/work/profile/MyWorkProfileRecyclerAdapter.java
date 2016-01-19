package com.zicure.abacconnect.work.profile;

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
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.my.deal.Deals;
import com.zicure.abacconnect.news.Newses;
import com.zicure.abacconnect.special.deals.SpecialDeals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DUMP129 on 9/28/2015.
 */
public class MyWorkProfileRecyclerAdapter extends RecyclerView.Adapter<MyWorkProfileRecyclerAdapter.ViewHolder> implements DataLayerListener{
    private List<WorkProfile> workProfileList;
    private Context mContext;
    private DataLayer dataLayer = null;
    private RecyclerView recyclerViewMyDeal = null;
    private MyWorkProfileRecyclerAdapter myWorkProfileRecyclerAdapter;

    public MyWorkProfileRecyclerAdapter(Context context, List<WorkProfile> workProfileList) {
        this.workProfileList = workProfileList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_profile_recycler_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.workProfile = workProfileList.get(position);

        holder.tvWorkProfileCompanyName.setText(workProfileList.get(position).company_name);

        String convert = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateWorkForm = workProfileList.get(position).work_from;
            if (dateWorkForm != null) {
                Date tmpDate = simpleDateFormat.parse(dateWorkForm);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("d MMM yyyy");
                convert = outputDateFormat.format(tmpDate);
                holder.tvWorkProfileWorkFrom.setText(convert);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateWorkTo = workProfileList.get(position).work_to;
            if (dateWorkTo != null) {
                Date tmpDate = simpleDateFormat.parse(dateWorkTo);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("d MMM yyyy");
                convert = outputDateFormat.format(tmpDate);
                holder.tvWorkProfileWorkTo.setText(convert);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvWorkProfilePosition.setText(workProfileList.get(position).work_position);
    }

    @Override
    public int getItemCount() {
        return workProfileList.size();
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
    public void fetchMyDeal(List<Deals> dealsList) {}

    @Override
    public void fetchMyWorkProfile(List<WorkProfile> workProfileList) {
        myWorkProfileRecyclerAdapter = new MyWorkProfileRecyclerAdapter(ApplicationContext.getInstance().getContext(), workProfileList);
        recyclerViewMyDeal.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext()));
        recyclerViewMyDeal.setAdapter(myWorkProfileRecyclerAdapter);
    }

    @Override
    public void fetchMyBusiness(List<MyBusiness> myBusinessList) {}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWorkProfileCompanyName, tvWorkProfileWorkFrom, tvWorkProfileWorkTo, tvWorkProfilePosition;
        public WorkProfile workProfile;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWorkProfileCompanyName = (TextView) itemView.findViewById(R.id.tvWorkProfileCompanyName);
            tvWorkProfileWorkFrom = (TextView) itemView.findViewById(R.id.tvWorkProfileWorkFrom);
            tvWorkProfileWorkTo = (TextView) itemView.findViewById(R.id.tvWorkProfileWorkTo);
            tvWorkProfilePosition = (TextView) itemView.findViewById(R.id.tvWorkProfilePosition);
        }
    }

    public void loadMyWorkProfile(int userId) {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.getMyWorkProfile(userId);
    }

    public void sendRecyclerView(RecyclerView recyclerViewMyDeal) {
        this.recyclerViewMyDeal = recyclerViewMyDeal;
    }
}
