package com.zicure.abacconnect.jobs;

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
 * Created by DUMP129 on 11/11/2015.
 */
public class SearchJobsTask extends AsyncTask<String, Void, String> {

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

        if ("searchjobs".equals(apiCurrent)) {
            List<Jobs> jobsList = new ArrayList<>();
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);
                jsonResult = jsonResult.getJSONObject("result");

                if ("OK".equals(jsonResult.getString("Success"))) {
                    jsonResult = jsonResult.getJSONObject("Data");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject = jsonResult.getJSONObject("Jobs");
                    Jobs jobs = new Jobs();
                    if (jsonObject.isNull("id")) {
                        jobs.id = null;
                    } else {
                        jobs.id = Integer.parseInt(jsonObject.getString("id"));
                    }

                    if (jsonObject.isNull("job_name")) {
                        jobs.job_name = null;
                    } else {
                        jobs.job_name = jsonObject.getString("job_name");
                    }

                    if (jsonObject.isNull("job_position")) {
                        jobs.job_position = null;
                    } else {
                        jobs.job_position = jsonObject.getString("job_position");
                    }

                    if (jsonObject.isNull("job_addr")) {
                        jobs.job_addr = null;
                    } else {
                        jobs.job_addr = jsonObject.getString("job_addr");
                    }

                    if (jsonObject.isNull("job_num")) {
                        jobs.job_num = null;
                    } else {
                        jobs.job_num = jsonObject.getString("job_num");
                    }

                    if (jsonObject.isNull("job_tel")) {
                        jobs.job_tel = null;
                    } else {
                        jobs.job_tel = jsonObject.getString("job_tel");
                    }

                    if (jsonObject.isNull("job_responsibility")) {
                        jobs.job_responsibility = null;
                    } else {
                        jobs.job_responsibility = jsonObject.getString("job_responsibility");
                    }

                    if (jsonObject.isNull("job_qualification")) {
                        jobs.job_qualification = jsonObject.getString("job_qualification");
                    } else {
                        jobs.job_qualification = jsonObject.getString("job_qualification");
                    }

                    if (jsonObject.isNull("job_group_id")) {
                        jobs.job_group_id = null;
                    } else {
                        jobs.job_group_id = Integer.parseInt(jsonObject.getString("job_group_id"));
                    }

                    if (jsonObject.isNull("student_id")) {
                        jobs.student_id = null;
                    } else {
                        jobs.student_id = jsonObject.getInt("student_id");
                    }

                    if (jsonObject.isNull("job_expiry_date")) {
                        jobs.job_expiry_date = null;
                    } else {
                        jobs.job_expiry_date = jsonObject.getString("job_expiry_date");
                    }

                    if (jsonObject.isNull("created")) {
                        jobs.created = null;
                    } else {
                        jobs.created = jsonObject.getString("created");
                    }

                    jobsList.add(jobs);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            asyncTaskListener.onTaskComplete("searchjobs", jobsList);
        }

        super.onPostExecute(result);
    }
}