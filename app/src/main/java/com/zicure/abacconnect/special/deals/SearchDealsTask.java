package com.zicure.abacconnect.special.deals;

import android.os.AsyncTask;

import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.api.ApiConfig;
import com.zicure.abacconnect.api.AsyncTaskListener;
import com.zicure.abacconnect.net.MyHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by F0HZZ52 on 10/02/2016.
 */
public class SearchDealsTask extends AsyncTask<String, Void, String> {
    private String responseString = "", url = ApiConfig.API_URL, apiCurrent = "";
    private AsyncTaskListener asyncTaskListener = null;

    public void setAsyncTaskListener(AsyncTaskListener asyncTaskListener) {
        this.asyncTaskListener = asyncTaskListener;
    }

    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; i < params.length; i++) {
            url += "/" + params[i];
            if (i == 0) {
                apiCurrent = params[i];
            }
        }

        url += ".json";

        MyHttpClient client = MyHttpClient.getInstance(ApplicationContext.getInstance().getContext());
        HttpGet httpGet = new HttpGet(url);
        StringBuilder str = new StringBuilder();
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
            responseString = str.toString();
        } catch (ClientProtocolException cpe) {
            responseString = cpe.getMessage();
            cpe.printStackTrace();
        } catch (IOException e) {
            responseString = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            responseString = e.getMessage();
            e.printStackTrace();
        }

        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        if ("searchDeal".equals(apiCurrent)) {
            List<SpecialDeals> specialDealsList = new ArrayList<SpecialDeals>();
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);
                jsonResult = jsonResult.getJSONObject("result");
                if ("OK".equals(jsonResult.getString("Success"))) {
                    jsonResult = jsonResult.getJSONObject("Data");

                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = jsonResult.getJSONArray("Deals");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        SpecialDeals specialDeals = new SpecialDeals();

                        if (jsonArray.getJSONObject(i).isNull("id")) {
                            specialDeals.id = null;
                        } else {
                            specialDeals.id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
                        }

                        if (jsonArray.getJSONObject(i).isNull("deal_name")) {
                            specialDeals.deal_name = null;
                        } else {
                            specialDeals.deal_name = jsonArray.getJSONObject(i).getString("deal_name");
                        }

                        if (jsonArray.getJSONObject(i).isNull("deal_discount")) {
                            specialDeals.deal_discount = null;
                        } else {
                            specialDeals.deal_discount = jsonArray.getJSONObject(i).getString("deal_discount");
                        }

                        if (jsonArray.getJSONObject(i).isNull("deal_expiry_date")) {
                            specialDeals.deal_expiry_date = null;
                        } else {
                            specialDeals.deal_expiry_date = jsonArray.getJSONObject(i).getString("deal_expiry_date");
                        }

                        if (jsonArray.getJSONObject(i).isNull("deal_condition")) {
                            specialDeals.deal_condition = null;
                        } else {
                            specialDeals.deal_condition = jsonArray.getJSONObject(i).getString("deal_condition");
                        }

                        if (jsonArray.getJSONObject(i).isNull("deal_detail")) {
                            specialDeals.deal_detail = null;
                        } else {
                            specialDeals.deal_detail = jsonArray.getJSONObject(i).getString("deal_detail");
                        }

                        if (jsonArray.getJSONObject(i).isNull("deal_thumbnail")) {
                            specialDeals.deal_thumbnail = null;
                        } else {
                            specialDeals.deal_thumbnail = jsonArray.getJSONObject(i).getString("deal_thumbnail");
                        }

                        if (jsonArray.getJSONObject(i).isNull("deal_path")) {
                            specialDeals.deal_path = null;
                        } else {
                            specialDeals.deal_path = jsonArray.getJSONObject(i).getString("deal_path");
                        }

                        specialDealsList.add(specialDeals);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            asyncTaskListener.onTaskComplete("searchDeal", specialDealsList);
        }
        super.onPostExecute(result);
    }
}
