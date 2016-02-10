package com.zicure.abacconnect.jobs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zicure.abacconnect.R;

import java.util.List;

/**
 * Created by DUMP129 on 10/26/2015.
 */
public class JobsViewFragment extends Fragment {
    private TextView tvJobsViewPositionName, tvJobsViewCompanyName, tvJobsViewAddressName,
            tvJobsViewPosition, tvJobsViewContactTel, tvJobsViewKeyAccountDetail, tvJobsViewQualificationDetail;
    private String posName, company, address, date, jobNum, contactTel, keyAccountDetail, qualificationDetail;

    private View v;
    private List<Jobs> jobsList;
    private int position;

    public JobsViewFragment(View v, List<Jobs> listJobs, int position) {
        this.v = v;
        this.jobsList = listJobs;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jobs_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvJobsViewPositionName = (TextView) view.findViewById(R.id.tvJobsViewPositionName);
        tvJobsViewCompanyName = (TextView) view.findViewById(R.id.tvJobsViewCompanyName);
        tvJobsViewAddressName = (TextView) view.findViewById(R.id.tvJobsViewAddressName);
        tvJobsViewPosition = (TextView) view.findViewById(R.id.tvJobsViewPosition);
        tvJobsViewContactTel = (TextView) view.findViewById(R.id.tvJobsViewContactTel);
        tvJobsViewKeyAccountDetail = (TextView) view.findViewById(R.id.tvJobsViewKeyAccountDetail);
        tvJobsViewQualificationDetail = (TextView) view.findViewById(R.id.tvJobsViewQualificationDetail);

        setData();
    }

    private void setData() {

        if (jobsList.get(position).job_position == null) {
            tvJobsViewPositionName.setText("");
        } else {
            tvJobsViewPositionName.setText(jobsList.get(position).job_position);
        }

        if (jobsList.get(position).job_name == null) {
            tvJobsViewCompanyName.setText("");
        } else {
            tvJobsViewCompanyName.setText(jobsList.get(position).job_name); // Company Name
        }

        if (jobsList.get(position).job_addr == null) {
            tvJobsViewAddressName.setText("");
        } else {
            tvJobsViewAddressName.setText(jobsList.get(position).job_addr);
        }

        if (jobsList.get(position).job_num == null) {
            tvJobsViewPosition.setText("");
        } else {
            tvJobsViewPosition.setText(jobsList.get(position).job_num);
        }

        if (jobsList.get(position).job_tel == null) {
            tvJobsViewContactTel.setText("");
        } else {
            tvJobsViewContactTel.setText(jobsList.get(position).job_tel);
        }

        if (jobsList.get(position).job_responsibility == null) {
            tvJobsViewKeyAccountDetail.setText("");
        } else {
            tvJobsViewKeyAccountDetail.setText(jobsList.get(position).job_responsibility); // Key Accountabilities
        }

        if (jobsList.get(position).job_qualification == null) {
            tvJobsViewQualificationDetail.setText(qualificationDetail);
        } else {
            tvJobsViewQualificationDetail.setText(jobsList.get(position).job_qualification);
        }
    }
}
