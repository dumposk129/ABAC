package com.zicure.abacconnect.my.deal;

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
 * Created by DUMP129 on 11/25/2015.
 */
public class FetchMyDealTask extends AsyncTask<String, Void, String> {
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

    /**
     * This method is decode JSONObject to JSOnArray
     * When finish call asyncTaskListener.onTaskComplete then send specific url and yearStr
     *
     * @return void
     */
    @Override
    protected void onPostExecute(String result) {
        if ("mydeal".equals(apiCurrent)) {
            List<Deals> dealsList = new ArrayList<Deals>();
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);
                jsonResult = jsonResult.getJSONObject("result");
                if ("OK".equals(jsonResult.getString("Success"))) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = jsonResult.getJSONArray("Data");

                    JSONObject jsonObject = new JSONObject();

                    for (int i = 0; i < jsonArray.length(); i++) {

                       jsonObject = jsonArray.getJSONObject(i).getJSONObject("Deal");

                        Deals deals = new Deals();

                        if (jsonObject.isNull("id")) {
                            deals.id = null;
                        } else {
                            deals.id = Integer.parseInt(jsonObject.getString("id"));
                        }

                        if (jsonObject.isNull("deal_name")) {
                            deals.deal_name = null;
                        } else {
                            deals.deal_name = jsonObject.getString("deal_name");
                        }

                        if (jsonObject.isNull("deal_discount")) {
                            deals.deal_discount = null;
                        } else {
                            deals.deal_discount = jsonObject.getString("deal_discount");
                        }

                        if (jsonObject.isNull("deal_expiry_date")) {
                            deals.deal_expiry_date = null;
                        } else {
                            deals.deal_expiry_date = jsonObject.getString("deal_expiry_date");
                        }

                        if (jsonObject.isNull("deal_condition")) {
                            deals.deal_condition = null;
                        } else {
                            deals.deal_condition = jsonObject.getString("deal_condition");
                        }

                        if (jsonObject.isNull("deal_detail")) {
                            deals.deal_detail = null;
                        } else {
                            deals.deal_detail = jsonObject.getString("deal_detail");
                        }

                        if (jsonObject.isNull("deal_thumbnail")) {
                            deals.deal_thumbnail = null;
                        } else {
                            deals.deal_thumbnail = jsonObject.getString("deal_thumbnail");
                        }

                        if (jsonObject.isNull("deal_path")) {
                            deals.deal_path = null;
                        } else {
                            deals.deal_path = jsonObject.getString("deal_path");
                        }

                        dealsList.add(deals);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            asyncTaskListener.onTaskComplete("mydeal", dealsList);
        }
        super.onPostExecute(result);
    }
}
