package com.zicure.abacconnect.api;

import android.content.Context;

import com.zicure.abacconnect.alumni.search.Alumni;
import com.zicure.abacconnect.alumni.search.AlumniSearchTask;
import com.zicure.abacconnect.alumni.search.SearchTask;
import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.business.connect.FetchBusinessConnectTask;
import com.zicure.abacconnect.jobs.FetchJobsTask;
import com.zicure.abacconnect.jobs.Jobs;
import com.zicure.abacconnect.jobs.SearchJobsTask;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.magazines.AddViewCountTask;
import com.zicure.abacconnect.magazines.LoadMagazinesTask;
import com.zicure.abacconnect.magazines.LoadYearTask;
import com.zicure.abacconnect.my.business.FetchMyBusinessTask;
import com.zicure.abacconnect.my.business.MyBusiness;
import com.zicure.abacconnect.my.deal.Deals;
import com.zicure.abacconnect.my.deal.FetchMyDealTask;
import com.zicure.abacconnect.news.AddViewCountNewsTask;
import com.zicure.abacconnect.news.News;
import com.zicure.abacconnect.news.FetchNewsTask;
import com.zicure.abacconnect.business.connect.SearchBusinessTask;
import com.zicure.abacconnect.news.SearchNewsTask;
import com.zicure.abacconnect.special.deals.ClaimDealTask;
import com.zicure.abacconnect.special.deals.FetchDealsTask;
import com.zicure.abacconnect.special.deals.SearchDealsTask;
import com.zicure.abacconnect.special.deals.SpecialDeals;
import com.zicure.abacconnect.work.profile.FetchMyWorkTask;
import com.zicure.abacconnect.work.profile.WorkProfile;

import java.util.List;

/**
 * Created by DUMP129 on 10/1/2015.
 */
public class DataLayer implements AsyncTaskListener {
    private Context context;
    private DataLayerListener dataLayerListener;
    private LoadYearTask loadYearTask;
    private LoadMagazinesTask loadMagazinesDataTask;
    private AddViewCountTask addViewCountTask;
    private FetchNewsTask fetchNewsTask;
    private FetchDealsTask fetchDealsTask;
    private ClaimDealTask claimDealTask;
    private FetchBusinessConnectTask fetchBusinessConnectTask;
    private SearchBusinessTask searchBusinessTask;
    private SearchNewsTask searchNewsTask;
    private SearchJobsTask searchJobsTask;
    private AlumniSearchTask alumniSearchTask;
    private FetchJobsTask fetchJobsTask;
    private SearchTask searchTask;
    private FetchMyDealTask fetchMyDealTask;
    private FetchMyWorkTask fetchMyWorkTask;
    private FetchMyBusinessTask fetchMyBusinessTask;
    private AddViewCountNewsTask addViewCountNewsTask;
    private SearchDealsTask searchDealsTask;

     private AsyncTaskListener asyncTaskListener = null;

    public DataLayer(Context context) {
        this.context = context;
    }

    public void setDataLayerListener(DataLayerListener dataLayerListener) {
        this.dataLayerListener = dataLayerListener;
    }

    public void setAsyncTaskListener(AsyncTaskListener asyncTaskListener) {
        this.asyncTaskListener = asyncTaskListener;
    }

    // Check AsyncTask is completed.
    @Override
    public void onTaskComplete(String action, Object result) {
        if ("getYears".equals(action) && result != null) {
            dataLayerListener.addYears((List<String>) result);
        } else if ("getMagazinesByYear".equals(action) && result != null) {
            dataLayerListener.addMagazine((List<Magazine>) result);
        } else if ("addViewCount".equals(action) && result != null) {
            dataLayerListener.addViewCounts((String) result);
        } else if ("fetchNews".equals(action) && result != null) {
            dataLayerListener.fetchNews((List<News>) result);
        } else if ("fetchDeals".equals(action) && result != null) {
            dataLayerListener.fetchSpecialDeals((List<SpecialDeals>) result);
        } else if ("fetchBusiness".equals(action) && result != null) {
            dataLayerListener.fetchBusiness((List<BusinessConnections>) result);
        } else if ("searchnNews".equals(action) && result != null) {
            dataLayerListener.fetchNews((List<News>) result);
        } else if ("searchBusiness".equals(action) && result != null) {
            dataLayerListener.fetchBusiness((List<BusinessConnections>) result);
        } else if ("fetchJobs".equals(action) && result != null) {
            dataLayerListener.fetchJobs((List<Jobs>) result);
        } else if ("searchjobs".equals(action) && result != null) {
            dataLayerListener.fetchJobs((List<Jobs>) result);
        } else if ("searchAlumni".equals(action) && result != null) {
            dataLayerListener.fetchAlumni((List<Alumni>) result);
        } else if ("fetchAlumni".equals(action) && result != null) {
            dataLayerListener.fetchAlumni((List<Alumni>) result);
        } else if ("mydeal".equals(action) && result != null) {
            dataLayerListener.fetchMyDeal((List<Deals>) result);
        } else if ("mywork".equals(action) && result != null) {
            dataLayerListener.fetchMyWorkProfile((List<WorkProfile>) result);
        } else if ("mybusiness".equals(action) && result != null) {
            dataLayerListener.fetchMyBusiness((List<MyBusiness>) result);
        } else if ("countviewnew".equals(action) && result != null) {
            dataLayerListener.addViewCounts((String) result);
        } else if ("searchDeal".equals(action) && result != null) {
            dataLayerListener.fetchSpecialDeals((List<SpecialDeals>) result);
        }
    }

    // Load Years
    public void loadYears() {
        loadYearTask = new LoadYearTask();
        loadYearTask.setAsyncTaskListener(this);
        loadYearTask.execute("getYears");
    }

    // Load Magazines
    public void loadMagazines(String year) {
        loadMagazinesDataTask = new LoadMagazinesTask();
        loadMagazinesDataTask.setAsyncTaskListener(this);
        loadMagazinesDataTask.execute("getMagazinesByYear", year);
    }

    // AddViewCount
    public void addViewCount(Integer magId) {
        addViewCountTask = new AddViewCountTask();
        addViewCountTask.setAsyncTaskListener(this);
        addViewCountTask.execute("addViewCount", Integer.toString(magId));
    }

    // FetchNews
    public void getNews() {
        fetchNewsTask = new FetchNewsTask();
        fetchNewsTask.setAsyncTaskListener(this);
        fetchNewsTask.execute("fetchNews");
    }

    public void addViewCountNews(Integer newsId) {
        addViewCountNewsTask = new AddViewCountNewsTask();
        addViewCountNewsTask.setAsyncTaskListener(this);
        addViewCountNewsTask.execute("countviewnew", Integer.toString(newsId));
    }

    // FetchDeals
    public void getDeals(int userId) {
        fetchDealsTask = new FetchDealsTask();
        fetchDealsTask.setAsyncTaskListener(this);
        fetchDealsTask.execute("fetchDeals", Integer.toString(userId));
    }

    public void claimDeal(Integer userId, Integer specialDealId) {
        claimDealTask = new ClaimDealTask();
        claimDealTask.setAsyncTaskListener(this);
        claimDealTask.execute("claimDeal", Integer.toString(userId), Integer.toString(specialDealId));
    }

    // FetchBusiness
    public void getBusiness() {
        fetchBusinessConnectTask = new FetchBusinessConnectTask();
        fetchBusinessConnectTask.setAsyncTaskListener(this);
        fetchBusinessConnectTask.execute("fetchBusiness");
    }

    // SearchBusiness
    public void searchBusiness(String searchText) {
        searchBusinessTask = new SearchBusinessTask();
        searchBusinessTask.setAsyncTaskListener(this);
        searchBusinessTask.execute("searchBusiness", searchText);
    }

    // SearchNews
    public void searchNews(String searchText) {
        searchNewsTask = new SearchNewsTask();
        searchNewsTask.setAsyncTaskListener(this);
        searchNewsTask.execute("searchnNews", searchText);
    }

    // SearchJobs
    public void searchJobs(String searchText) {
        searchJobsTask = new SearchJobsTask();
        searchJobsTask.setAsyncTaskListener(this);
        searchJobsTask.execute("searchjobs", searchText);
    }

    // SearchAlumni
    public void searchAlumni(String searchText) {
        searchTask = new SearchTask();
        searchTask.setAsyncTaskListener(this);
        searchTask.execute("searchAlumni", searchText);
    }

    // FetchAlumni
    public void getAlumni() {
        alumniSearchTask = new AlumniSearchTask();
        alumniSearchTask.setAsyncTaskListener(this);
        alumniSearchTask.execute("fetchAlumni");
    }

    // FetchJobs
    public void getJobs() {
        fetchJobsTask = new FetchJobsTask();
        fetchJobsTask.setAsyncTaskListener(this);
        fetchJobsTask.execute("fetchJobs");
    }

    // FetchMyDeal
    public void getMyDeal(int userId) {
        fetchMyDealTask = new FetchMyDealTask();
        fetchMyDealTask.setAsyncTaskListener(this);
        fetchMyDealTask.execute("mydeal", Integer.toString(userId));
    }

    // FetchMyWorkProfile
    public void getMyWorkProfile(int userId) {
        fetchMyWorkTask = new FetchMyWorkTask();
        fetchMyWorkTask.setAsyncTaskListener(this);
        fetchMyWorkTask.execute("mywork", Integer.toString(userId));
    }

    public void getMyBusiness(int userId) {
        fetchMyBusinessTask = new FetchMyBusinessTask();
        fetchMyBusinessTask.setAsyncTaskListener(this);
        fetchMyBusinessTask.execute("mybusiness", Integer.toString(userId));
    }

    public void searchDeals(String searchText) {
        searchDealsTask = new SearchDealsTask();
        searchDealsTask.setAsyncTaskListener(this);
        searchDealsTask.execute("searchDeal", searchText);
    }
}