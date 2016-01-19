package com.zicure.abacconnect.my.deal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.R;


/**
 * Created by DUMP129 on 9/29/2015.
 */
public class MyDealsFragment extends Fragment {
    private RecyclerView myDealsRecyclerView = null;
    private MyDealsRecyclerAdapter myDealsRecyclerAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_deals, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ABACLogin", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        myDealsRecyclerView = (RecyclerView) view.findViewById(R.id.my_deals_recycler_view);
        myDealsRecyclerAdapter = new MyDealsRecyclerAdapter(ApplicationContext.getInstance().getContext(), null);
        myDealsRecyclerAdapter.sendRecyclerView(myDealsRecyclerView);
        myDealsRecyclerAdapter.loadMyDeal(Integer.parseInt(userId));
    }
}
