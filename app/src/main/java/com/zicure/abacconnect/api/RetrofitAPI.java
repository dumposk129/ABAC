package com.zicure.abacconnect.api;

import com.zicure.abacconnect.business.connect.BusinessConnections;

import retrofit.Call;
import retrofit.http.POST;

/**
 * Created by DUMP129 on 12/9/2015.
 */
public interface RetrofitAPI {
    @POST("fetchBusiness")
    Call<BusinessConnections> loadBusiness();
}
