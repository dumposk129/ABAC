package com.zicure.abacconnect.my.business;

import android.os.AsyncTask;

import com.zicure.abacconnect.ApplicationContext;
import com.zicure.abacconnect.api.ApiConfig;
import com.zicure.abacconnect.api.AsyncTaskListener;
import com.zicure.abacconnect.net.MyHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
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
public class FetchMyBusinessTask extends AsyncTask<String, Void, String> {
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
        if ("mybusiness".equals(apiCurrent)) {
            List<MyBusiness> myBusinessList = new ArrayList<MyBusiness>();
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);
                jsonResult = jsonResult.getJSONObject("result");
                if ("OK".equals(jsonResult.getString("Success"))) {
                    jsonResult = jsonResult.getJSONObject("Data");

                    jsonResult = jsonResult.getJSONObject("Business");
                    MyBusiness myBusiness = new MyBusiness();

                    if (jsonResult.isNull("id")) {
                        myBusiness.id = null;
                    } else {
                        myBusiness.id = Integer.parseInt(jsonResult.getString("id"));
                    }

                    if (jsonResult.isNull("company_name")) {
                        myBusiness.company_name = null;
                    } else {
                        myBusiness.company_name = jsonResult.getString("company_name");
                    }

                    if (jsonResult.isNull("short_description")) {
                        myBusiness.short_description = null;
                    } else {
                        myBusiness.short_description = jsonResult.getString("short_description");
                    }

                    if (jsonResult.isNull("long_description")) {
                        myBusiness.long_description = null;
                    } else {
                        myBusiness.long_description = jsonResult.getString("long_description");
                    }

                    if (jsonResult.isNull("street_address")) {
                        myBusiness.street_address = null;
                    } else {
                        myBusiness.street_address = jsonResult.getString("street_address");
                    }

                    if (jsonResult.isNull("province_name_eng")) {
                        myBusiness.province_name_eng = null;
                    } else {
                        myBusiness.province_name_eng = jsonResult.getString("province_name_eng");
                    }

                    if (jsonResult.isNull("district_name_eng")) {
                        myBusiness.district_name_eng = null;
                    } else {
                        myBusiness.district_name_eng = jsonResult.getString("district_name_eng");
                    }

                    if (jsonResult.isNull("locality_name_eng")) {
                        myBusiness.locality_name_eng = null;
                    } else {
                        myBusiness.locality_name_eng = jsonResult.getString("locality_name_eng");
                    }

                    if (jsonResult.isNull("zipcode")) {
                        myBusiness.zipcode = null;
                    } else {
                        myBusiness.zipcode = jsonResult.getString("zipcode");
                    }

                    if (jsonResult.isNull("contact_person")) {
                        myBusiness.contact_person = null;
                    } else {
                        myBusiness.contact_person = jsonResult.getString("contact_person");
                    }

                    if (jsonResult.isNull("contact_email")) {
                        myBusiness.contact_email = null;
                    } else {
                        myBusiness.contact_email = jsonResult.getString("contact_email");
                    }

                    if (jsonResult.isNull("contact_phone")) {
                        myBusiness.contact_phone = null;
                    } else {
                        myBusiness.contact_phone = jsonResult.getString("contact_phone");
                    }

                    if (jsonResult.isNull("contact_position")) {
                        myBusiness.contact_position = null;
                    } else {
                        myBusiness.contact_position = jsonResult.getString("contact_position");
                    }

                    if (jsonResult.isNull("created")) {
                        myBusiness.created = null;
                    } else {
                        myBusiness.created = jsonResult.getString("created");
                    }

                    myBusinessList.add(myBusiness);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            asyncTaskListener.onTaskComplete("mybusiness", myBusinessList);
        }
        super.onPostExecute(result);
    }
}
