package com.zicure.abacconnect.work.profile;

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
public class FetchMyWorkTask extends AsyncTask<String, Void, String> {
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
        if ("mywork".equals(apiCurrent)) {
            List<WorkProfile> workProfileList = new ArrayList<WorkProfile>();
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);
                jsonResult = jsonResult.getJSONObject("result");
                if ("OK".equals(jsonResult.getString("Success"))) {
                    jsonResult = jsonResult.getJSONObject("Data");

                    jsonResult = jsonResult.getJSONObject("Work");
                    WorkProfile workProfile = new WorkProfile();

                    if (jsonResult.isNull("id")) {
                        workProfile.id = null;
                    } else {
                        workProfile.id = Integer.parseInt(jsonResult.getString("id"));
                    }

                    if (jsonResult.isNull("work_from")) {
                        workProfile.work_from = null;
                    } else {
                        workProfile.work_from = jsonResult.getString("work_from");
                    }

                    if (jsonResult.isNull("work_to")) {
                        workProfile.work_to = null;
                    } else {
                        workProfile.work_to = jsonResult.getString("work_to");
                    }

                    if (jsonResult.isNull("company_name")) {
                        workProfile.company_name = null;
                    } else {
                        workProfile.company_name = jsonResult.getString("company_name");
                    }

                    if (jsonResult.isNull("work_position")) {
                        workProfile.work_position = null;
                    } else {
                        workProfile.work_position = jsonResult.getString("work_position");
                    }

                    if (jsonResult.isNull("user_id")) {
                        workProfile.user_id = null;
                    } else {
                        workProfile.user_id = jsonResult.getString("user_id");
                    }

                    workProfileList.add(workProfile);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            asyncTaskListener.onTaskComplete("mywork", workProfileList);
        }
        super.onPostExecute(result);
    }
}