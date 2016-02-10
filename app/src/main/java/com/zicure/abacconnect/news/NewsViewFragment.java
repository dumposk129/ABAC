package com.zicure.abacconnect.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zicure.abacconnect.R;
import com.zicure.abacconnect.api.ApiConfig;

import java.util.List;

/**
 * Created by DUMP129 on 10/27/2015.
 */
public class NewsViewFragment extends Fragment {
    private ImageView imgViewNewsView;
    private TextView tvNewsViewNewsTopic, tvNewsViewNewsIntro, tvNewsViewNewsBody;
    private View v;
    private List<News> newsList;
    private int position;

    public NewsViewFragment(View v, List<News> listNewses, int position) {
        this.v = v;
        this.newsList = listNewses;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgViewNewsView = (ImageView) view.findViewById(R.id.imgViewNewsView);
        tvNewsViewNewsTopic = (TextView) view.findViewById(R.id.newsViewDetailNewsTopic);
        tvNewsViewNewsIntro = (TextView) view.findViewById(R.id.newsViewDetailNewsIntro);
        tvNewsViewNewsBody = (TextView) view.findViewById(R.id.newsViewDetailNewsBody);

        setData();
    }

    private void setData() {
        if (newsList.get(position).news_topic == null) {
            tvNewsViewNewsTopic.setText("");
        } else {
            tvNewsViewNewsTopic.setText(newsList.get(position).news_topic);
        }

        if (newsList.get(position).news_intro == null) {
            tvNewsViewNewsIntro.setText("");
        } else {
            tvNewsViewNewsIntro.setText(newsList.get(position).news_intro);
        }

        if (newsList.get(position).news_body == null) {
            tvNewsViewNewsBody.setText("");
        } else {
            tvNewsViewNewsBody.setText(newsList.get(position).news_body);
        }

        if (newsList.get(position).news_thumbnail == null) {

        } else {
            String imgUrl = ApiConfig.IMG_URL + newsList.get(position).news_path;
            Glide.with(getActivity())
                    .load(imgUrl)
                    .centerCrop()
                    .into(imgViewNewsView);
        }
    }
}