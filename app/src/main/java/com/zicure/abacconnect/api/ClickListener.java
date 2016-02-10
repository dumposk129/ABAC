package com.zicure.abacconnect.api;

import android.view.View;

import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.jobs.Jobs;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.news.News;
import com.zicure.abacconnect.special.deals.SpecialDeals;

import java.util.List;

/**
 * Created by DUMP129 on 10/13/2015.
 */
public interface ClickListener {
    public void onClickInMagazineListener(View v, int position, List<Magazine> listMagazines);
    public void onNewsClickListener(View v, List<News> listNewses, int position);
    public void onSpecialDealsClickListener(View v, List<SpecialDeals> listDeals, int position);
    public void firstDataListener(int position, List<Magazine> magazineList);
    public void onBusinessClickListener(View v, List<BusinessConnections> listBusiness, int position);
    public void onJobsClickListener(View v, List<Jobs> listJobs, int position);
}
