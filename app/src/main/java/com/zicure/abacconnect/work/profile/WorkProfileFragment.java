package com.zicure.abacconnect.work.profile;

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
 * Created by DUMP129 on 9/28/2015.
 */
public class WorkProfileFragment extends Fragment {
    private RecyclerView workProfileRecyclerView = null;
    private MyWorkProfileRecyclerAdapter myWorkProfileRecyclerAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ABACLogin", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        workProfileRecyclerView = (RecyclerView) view.findViewById(R.id.work_profile_recycler_view);

        myWorkProfileRecyclerAdapter = new MyWorkProfileRecyclerAdapter(ApplicationContext.getInstance().getContext(), null);
        myWorkProfileRecyclerAdapter.sendRecyclerView(workProfileRecyclerView);
        myWorkProfileRecyclerAdapter.loadMyWorkProfile(Integer.parseInt(userId));
    }
}