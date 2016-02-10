package com.zicure.abacconnect.api;

import com.zicure.abacconnect.alumni.search.Alumni;
import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.jobs.Jobs;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.my.deal.Deals;
import com.zicure.abacconnect.news.News;
import com.zicure.abacconnect.special.deals.SpecialDeals;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.util.List;

/**
 * Created by DUMP129 on 10/1/2015.
 */

public interface DataLayerListener {
    public void addMagazine(List<Magazine> magazineList);
    public void addYears(List<String> years);
    public void addViewCounts(String viewCount);
    public void fetchNews(List<News> newsList);
    public void fetchSpecialDeals(List<SpecialDeals> specialDealList);
    public void fetchBusiness(List<BusinessConnections> businessConnectionsList);
    public void fetchAlumni(List<Alumni> usersList);
    public void fetchJobs(List<Jobs> jobsList);
    public void fetchMyDeal(List<Deals> dealsList);
    public void fetchMyWorkProfile(List<WorkProfile> workProfileList);
    public void fetchMyBusiness(List<MyBusiness> myBusinessList);
}
