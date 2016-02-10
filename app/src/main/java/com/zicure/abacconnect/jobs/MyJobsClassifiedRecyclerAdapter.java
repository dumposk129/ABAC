package com.zicure.abacconnect.jobs;

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
import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.my.deal.Deals;
import com.zicure.abacconnect.news.News;
import com.zicure.abacconnect.special.deals.SpecialDeals;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DUMP129 on 9/28/2015.
 */
public class MyJobsClassifiedRecyclerAdapter extends RecyclerView.Adapter<MyJobsClassifiedRecyclerAdapter.ViewHolder> implements DataLayerListener, ClickListener {
    private DataLayer dataLayer = null;
    private RecyclerView recyclerViewJobs = null;
    private ClickListener clickListener = null;
    private int position;
    private Context mContext;
    private List<Jobs> jobsList = null;
    private MyJobsClassifiedRecyclerAdapter myJobsClassifiedRecyclerAdapter = null;

    public void setMyJobsClassifiedListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public MyJobsClassifiedRecyclerAdapter(Context context, List<Jobs> jobsList) {
        this.jobsList = jobsList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_jobs_classified_recycler_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.jobs = jobsList.get(position);

        if (jobsList.get(position).job_position == null) {
            holder.tvJobsPositionName.setText("");
        } else {
            holder.tvJobsPositionName.setText(jobsList.get(position).job_position);
        }

        if (jobsList.get(position).job_name == null) {
            holder.tvJobsCompanyName.setText("");
        } else {
            holder.tvJobsCompanyName.setText(jobsList.get(position).job_name); // Company Name
        }

        if (jobsList.get(position).job_addr == null) {
            holder.tvJobsAddress.setText("");
        } else {
            holder.tvJobsAddress.setText(jobsList.get(position).job_addr);
        }

        if (jobsList.get(position).job_num == null) {
            holder.tvJobsPosition.setText("");
        } else {
            holder.tvJobsPosition.setText(jobsList.get(position).job_num);
        }

        String convert = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateCreated = jobsList.get(position).created;
            if (dateCreated != null) {
                Date tmpDate = simpleDateFormat.parse(dateCreated);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("d MMM yyyy");
                convert = outputDateFormat.format(tmpDate);
                holder.tvJobsDate.setText(convert);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    @Override
    public void onClickInMagazineListener(View v, int position, List<Magazine> listMagazines) {
    }

    @Override
    public void onNewsClickListener(View v, List<News> listNewses, int position) {
    }

    @Override
    public void onSpecialDealsClickListener(View v, List<SpecialDeals> listDeals, int position) {
    }

    @Override
    public void firstDataListener(int position, List<Magazine> magazineList) {
    }

    @Override
    public void onBusinessClickListener(View v, List<BusinessConnections> listBusiness, int position) {
    }

    @Override
    public void onJobsClickListener(View v, List<Jobs> listJobs, int position) {
        if (clickListener != null) {
            clickListener.onJobsClickListener(v, listJobs, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJobsPositionName, tvJobsCompanyName, tvJobsAddress, tvJobsPosition, tvJobsDate;
        public Jobs jobs;

        public ViewHolder(View itemView) {
            super(itemView);

            tvJobsPositionName = (TextView) itemView.findViewById(R.id.tvJobsPositionName);
            tvJobsCompanyName = (TextView) itemView.findViewById(R.id.tvJobsCompanyName);
            tvJobsAddress = (TextView) itemView.findViewById(R.id.tvJobsAddress);
            tvJobsPosition = (TextView) itemView.findViewById(R.id.tvJobsPosition);
            tvJobsDate = (TextView) itemView.findViewById(R.id.tvJobsDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        position = getAdapterPosition();
                        clickListener.onJobsClickListener(v, jobsList, position);
                    }
                }
            });
        }
    }

    public void loadSearch(String searchText) {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.searchJobs(searchText);
    }

    public void loadJobs() {
        dataLayer = new DataLayer(ApplicationContext.getInstance().getContext());
        dataLayer.setDataLayerListener(this);
        dataLayer.getJobs();
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
    public void fetchNews(List<News> newsList) {
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
    public void fetchJobs(List<Jobs> jobsList) {
        myJobsClassifiedRecyclerAdapter = new MyJobsClassifiedRecyclerAdapter(ApplicationContext.getInstance().getContext(), jobsList);
        myJobsClassifiedRecyclerAdapter.setMyJobsClassifiedListener(this);
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(ApplicationContext.getInstance().getContext()));
        recyclerViewJobs.setAdapter(myJobsClassifiedRecyclerAdapter);
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

    public void sendRecyclerView(RecyclerView recyclerViewJobs) {
        this.recyclerViewJobs = recyclerViewJobs;
    }
}