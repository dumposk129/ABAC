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
    private TextView tvBusinessViewDescription, tvBusinessViewCompanyName, tvBusinessViewAddressName, tvBusinessViewProductDetail,
            tvBusinessViewContactName, tvBusinessViewContactPosition, tvBusinessViewContactPhone, tvBusinessViewDate;
    private ImageView imgViewBusinessViewContact;
    private RecyclerView rvBusinessViewImg;
    private BusinessViewImgRecyclerAdapter businessViewImgRecyclerAdapter = null;
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
        return inflater.inflate(R.layout.fragment_business_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvBusinessViewCompanyName = (TextView) view.findViewById(R.id.tvBusinessViewCompanyName);
        tvBusinessViewDescription = (TextView) view.findViewById(R.id.tvBusinessViewPositionName);
        tvBusinessViewAddressName = (TextView) view.findViewById(R.id.tvBusinessViewAddressName);
        tvBusinessViewProductDetail = (TextView) view.findViewById(R.id.tvBusinessViewProductDetail);
        tvBusinessViewContactName = (TextView) view.findViewById(R.id.tvBusinessViewContactName);
        tvBusinessViewContactPosition = (TextView) view.findViewById(R.id.tvBusinessViewContactPosition);
        tvBusinessViewContactPhone = (TextView) view.findViewById(R.id.tvBusinessViewContactPhone);
        //tvBusinessViewDate = (TextView) view.findViewById(R.id.tvBusinessViewDate);
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
        String businessStreetAddress = null, businessLocalityNameEng = null, businessDistrictNameEng = null,
                businessProvinceNameEng = null, businessZipcode = null;

        if (businessConnectionsList.get(position).company_name == null) {
            tvBusinessViewCompanyName.setText("");
        } else {
            tvBusinessViewCompanyName.setText(businessConnectionsList.get(position).company_name);
        }

        if (businessConnectionsList.get(position).long_description == null) {
            tvBusinessViewDescription.setText("");
        } else {
            tvBusinessViewDescription.setText(businessConnectionsList.get(position).long_description);
        }

        if (businessConnectionsList.get(position).street_address == null) {
            businessStreetAddress = "";
        } else {
            businessStreetAddress = businessConnectionsList.get(position).street_address;
        }

        if (businessConnectionsList.get(position).locality_name_eng == null) {
            businessLocalityNameEng = "";
        } else {
            businessLocalityNameEng = businessConnectionsList.get(position).locality_name_eng;
        }

        if (businessConnectionsList.get(position).district_name_eng == null) {
            businessDistrictNameEng = "";
        } else {
            businessDistrictNameEng = businessConnectionsList.get(position).district_name_eng;
        }

        if (businessConnectionsList.get(position).province_name_eng == null) {
            businessProvinceNameEng = "";
        } else {
            businessProvinceNameEng = businessConnectionsList.get(position).province_name_eng;
        }

        if (businessConnectionsList.get(position).zipcode == null) {
            businessZipcode = "";
        } else {
            businessZipcode = businessConnectionsList.get(position).zipcode;
        }

        String businessAddress = businessStreetAddress + " " + businessLocalityNameEng
                + " " + businessDistrictNameEng + " " + businessProvinceNameEng + " " + businessZipcode;
        tvBusinessViewAddressName.setText(businessAddress);

        /*String convert = null;
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
        }*/

        if (businessConnectionsList.get(position).contact_thumbnail == null) {
            imgViewBusinessViewContact.setImageResource(R.mipmap.ic_profile_blue_64);
        } else {
            String imgContactUrl = ApiConfig.IMG_URL + businessConnectionsList.get(position).contact_thumbnail;
            Glide.with(ApplicationContext.getInstance().getContext()).load(imgContactUrl)
                    .centerCrop().into(imgViewBusinessViewContact);
        }

        if (businessConnectionsList.get(position).contact_person == null) {
            tvBusinessViewContactName.setText("");
        } else {
            tvBusinessViewContactName.setText(businessConnectionsList.get(position).contact_person);
        }

        if (businessConnectionsList.get(position).contact_position == null) {
            tvBusinessViewContactPosition.setText("");
        } else {
            tvBusinessViewContactPosition.setText(businessConnectionsList.get(position).contact_position);
        }

        if (businessConnectionsList.get(position).contact_phone == null) {
            tvBusinessViewContactPhone.setText("");
        } else {
            tvBusinessViewContactPhone.setText(businessConnectionsList.get(position).contact_phone);
        }
    }
}