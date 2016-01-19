package com.zicure.abacconnect.business.connect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.R;
import com.zicure.abacconnect.api.ApiConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DUMP129 on 10/27/2015.
 */
public class BusinessViewFragment extends Fragment {
    private TextView tvBusinessViewPositionName, tvBusinessViewCompanyName, tvBusinessViewAddressName, tvBusinessViewProductDetail,
            tvBusinessViewContactName, tvBusinessViewContactPosition, tvBusinessViewContactPhone, tvBusinessViewDate;
    private ImageView imgViewBusinessViewContact;
    private RecyclerView rvBusinessViewImg;
    private BusinessViewImgRecyclerAdapter businessViewImgRecyclerAdapter = null;
    private String businessCompanyName, businessPositionName, businessAddress, businessViewContactName, businessViewContactPosition,
            businessViewContactPhone;

    private View v;
    private List<BusinessConnections> businessConnectionsList;
    private int position;

    public BusinessViewFragment(View v, List<BusinessConnections> listBusiness, int position) {
        this.v = v;
        this.businessConnectionsList = listBusiness;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.business_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvBusinessViewCompanyName = (TextView) view.findViewById(R.id.tvBusinessViewCompanyName);
        tvBusinessViewPositionName = (TextView) view.findViewById(R.id.tvBusinessViewPositionName);
        tvBusinessViewAddressName = (TextView) view.findViewById(R.id.tvBusinessViewAddressName);
        tvBusinessViewProductDetail = (TextView) view.findViewById(R.id.tvBusinessViewProductDetail);
        tvBusinessViewContactName = (TextView) view.findViewById(R.id.tvBusinessViewContactName);
        tvBusinessViewContactPosition = (TextView) view.findViewById(R.id.tvBusinessViewContactPosition);
        tvBusinessViewContactPhone = (TextView) view.findViewById(R.id.tvBusinessViewContactPhone);
        tvBusinessViewDate = (TextView) view.findViewById(R.id.tvBusinessViewDate);
        imgViewBusinessViewContact = (ImageView) view.findViewById(R.id.imgViewBusinessViewContact);
        rvBusinessViewImg = (RecyclerView) view.findViewById(R.id.rvBusinessViewImg);

        addImageToHorRV();
        setData();
    }

    private void addImageToHorRV() {
        businessViewImgRecyclerAdapter = new BusinessViewImgRecyclerAdapter(ApplicationContext.getInstance().getContext(), null);
        businessViewImgRecyclerAdapter.sendRecyclerView(rvBusinessViewImg);
        businessViewImgRecyclerAdapter.loadImage();
    }

    private void setData() {
        businessCompanyName = businessConnectionsList.get(position).company_name;
        tvBusinessViewCompanyName.setText(businessCompanyName);

        businessPositionName = businessConnectionsList.get(position).long_description;
        tvBusinessViewPositionName.setText(businessPositionName);

        String businessAddress = businessConnectionsList.get(position).street_address + " " + businessConnectionsList.get(position).locality_name_eng
                + " " + businessConnectionsList.get(position).district_name_eng + " " + businessConnectionsList.get(position).province_name_eng
                + " " + businessConnectionsList.get(position).zipcode;
        tvBusinessViewAddressName.setText(businessAddress);

        String convert = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateCreated = businessConnectionsList.get(position).created;
            if (dateCreated != null) {
                Date tmpDate = simpleDateFormat.parse(dateCreated);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("d MMM yyyy");
                convert = outputDateFormat.format(tmpDate);
                tvBusinessViewDate.setText(convert);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String imgContactUrl = ApiConfig.IMG_URL + businessConnectionsList.get(position).contact_thumbnail;
        Glide.with(ApplicationContext.getInstance().getContext())
                .load(imgContactUrl)
                .centerCrop()
                .into(imgViewBusinessViewContact);

        businessViewContactName = businessConnectionsList.get(position).contact_person;
        tvBusinessViewContactName.setText(businessViewContactName);

        businessViewContactPosition = businessConnectionsList.get(position).contact_position;
        tvBusinessViewContactPosition.setText(businessViewContactPosition);

        businessViewContactPhone = businessConnectionsList.get(position).contact_phone;
        tvBusinessViewContactPhone.setText(businessViewContactPhone);
    }
}
