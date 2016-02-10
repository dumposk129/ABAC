package com.zicure.abacconnect.special.deals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.R;
import com.zicure.abacconnect.api.ClickListener;
import com.zicure.abacconnect.business.connect.BusinessConnections;
import com.zicure.abacconnect.jobs.Jobs;
import com.zicure.abacconnect.magazines.Magazine;
import com.zicure.abacconnect.news.News;

import java.util.List;

/**
 * Created by DUMP129 on 9/25/2015.
 */
public class SpecialDealsFragment extends Fragment implements ClickListener {
    private RecyclerView specialDealsRecyclerView = null;
    private MySpecialDealRecyclerAdapter mySpecialDealRecyclerAdapter = null;
    private SearchView searchDeals;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          return inflater.inflate(R.layout.fragment_special_deals, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ABACLogin", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        specialDealsRecyclerView = (RecyclerView) view.findViewById(R.id.special_deal_recycler_view);
        searchDeals = (SearchView) view.findViewById(R.id.searchDeals);
        searchDeals.setQueryHint("Restaurant");
        searchDeals.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mySpecialDealRecyclerAdapter = new MySpecialDealRecyclerAdapter(ApplicationContext.getInstance().getContext(), null);
        mySpecialDealRecyclerAdapter.setMySpecialDealListener(this);
        mySpecialDealRecyclerAdapter.sendRecyclerView(specialDealsRecyclerView);
        mySpecialDealRecyclerAdapter.loadDeals(Integer.parseInt(userId));
    }

    private void search(String searchString) {
        mySpecialDealRecyclerAdapter = new MySpecialDealRecyclerAdapter(ApplicationContext.getInstance().getContext(), null);
        mySpecialDealRecyclerAdapter.setMySpecialDealListener(this);
        mySpecialDealRecyclerAdapter.sendRecyclerView(specialDealsRecyclerView);
        mySpecialDealRecyclerAdapter.loadSearch(searchString);
    }

    @Override
    public void onClickInMagazineListener(View v, int position, List<Magazine> listMagazines) {}

    @Override
    public void onNewsClickListener(View v, List<News> listNewses, int position) {}

    @Override
    public void onSpecialDealsClickListener(View v, List<SpecialDeals> listDeals, int position) {
        Fragment fragment = new SpecialDealViewFragment(v, listDeals, position);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
        transaction.replace(R.id.main, fragment).commit();
    }

    @Override
    public void firstDataListener(int position, List<Magazine> magazineList) {}

    @Override
    public void onBusinessClickListener(View v, List<BusinessConnections> listBusiness, int position) {}

    @Override
    public void onJobsClickListener(View v, List<Jobs> listJobs, int position) {}
}
