package com.zicure.abacconnect.business.connect;

import android.os.AsyncTask;
import android.widget.Toast;

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
 * Created by DUMP129 on 11/9/2015.
 */
public class FetchBusinessConnectTask extends AsyncTask<String, Void, String> {
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
        if ("fetchBusiness".equals(apiCurrent)) {
            List<BusinessConnections> businessConnectionsList = new ArrayList<>();
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);
                jsonResult = jsonResult.getJSONObject("result");
                if ("OK".equals(jsonResult.getString("Success"))) {
                    jsonResult = jsonResult.getJSONObject("Data");

                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray = new JSONArray();

                    jsonArray = jsonResult.getJSONArray("Business");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BusinessConnections businessConnections = new BusinessConnections();
                        if (jsonArray.getJSONObject(i).isNull("id")) {
                            businessConnections.id = null;
                        } else {
                            businessConnections.id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
                        }

                        if (jsonArray.getJSONObject(i).isNull("company_name")) {
                            businessConnections.company_name = null;
                        } else {
                            businessConnections.company_name = jsonArray.getJSONObject(i).getString("company_name");
                        }

                        if (jsonArray.getJSONObject(i).isNull("short_description")) {
                            businessConnections.short_description = null;
                        } else {
                            businessConnections.short_description = jsonArray.getJSONObject(i).getString("short_description");
                        }

                        if (jsonArray.getJSONObject(i).isNull("street_address")) {
                            businessConnections.street_address = null;
                        } else {
                            businessConnections.street_address = jsonArray.getJSONObject(i).getString("street_address");
                        }

                        if (jsonArray.getJSONObject(i).isNull("province_name_eng")) {
                            businessConnections.province_name_eng = null;
                        } else {
                            businessConnections.province_name_eng = jsonArray.getJSONObject(i).getString("province_name_eng");
                        }

                        if (jsonArray.getJSONObject(i).isNull("district_name_eng")) {
                            businessConnections.district_name_eng = null;
                        } else {
                            businessConnections.district_name_eng = jsonArray.getJSONObject(i).getString("district_name_eng");
                        }

                        if (jsonArray.getJSONObject(i).isNull("locality_name_eng")) {
                            businessConnections.locality_name_eng = null;
                        } else {
                            businessConnections.locality_name_eng = jsonArray.getJSONObject(i).getString("locality_name_eng");
                        }

                        if (jsonArray.getJSONObject(i).isNull("zipcode")) {
                            businessConnections.zipcode = null;
                        } else {
                            businessConnections.zipcode = jsonArray.getJSONObject(i).getString("zipcode");
                        }

                        if (jsonArray.getJSONObject(i).isNull("long_description")) {
                            businessConnections.long_description = null;
                        } else {
                            businessConnections.long_description = jsonArray.getJSONObject(i).getString("long_description");
                        }

                        if (jsonArray.getJSONObject(i).isNull("contact_person")) {
                            businessConnections.contact_person = null;
                        } else {
                            businessConnections.contact_person = jsonArray.getJSONObject(i).getString("contact_person");
                        }

                        if (jsonArray.getJSONObject(i).isNull("contact_phone")) {
                            businessConnections.contact_phone = null;
                        } else {
                            businessConnections.contact_phone = jsonArray.getJSONObject(i).getString("contact_phone");
                        }

                        if (jsonArray.getJSONObject(i).isNull("contact_email")) {
                            businessConnections.contact_email = null;
                        } else {
                            businessConnections.contact_email = jsonArray.getJSONObject(i).getString("contact_email");
                        }

                        if (jsonArray.getJSONObject(i).isNull("contact_thumbnail")) {
                            businessConnections.contact_thumbnail = null;
                        } else {
                            businessConnections.contact_thumbnail = jsonArray.getJSONObject(i).getString("contact_thumbnail");
                        }

                        if (jsonArray.getJSONObject(i).isNull("contact_position")) {
                            businessConnections.contact_position = null;
                        } else {
                            businessConnections.contact_position = jsonArray.getJSONObject(i).getString("contact_position");
                        }

                        if (jsonArray.getJSONObject(i).isNull("business_thumbnail")) {
                            businessConnections.business_thumbnail = null;
                        } else {
                            businessConnections.business_thumbnail = jsonArray.getJSONObject(i).getString("business_thumbnail");
                        }

                        if (jsonArray.getJSONObject(i).isNull("notify_date")) {
                            businessConnections.notify_date = null;
                        } else {
                            businessConnections.notify_date = jsonArray.getJSONObject(i).getString("notify_date");
                        }

                        if (jsonArray.getJSONObject(i).isNull("created")) {
                            businessConnections.created = null;
                        } else {
                            businessConnections.created = jsonArray.getJSONObject(i).getString("created");
                        }

                        businessConnectionsList.add(businessConnections);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Toast.makeText(ApplicationContext.getInstance().getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            asyncTaskListener.onTaskComplete("fetchBusiness", businessConnectionsList);
        }
        super.onPostExecute(result);
    }
}
