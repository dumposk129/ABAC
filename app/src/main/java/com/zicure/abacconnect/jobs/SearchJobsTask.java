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
            List<Jobss> jobssList = new ArrayList<>();
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);
                jsonResult = jsonResult.getJSONObject("result");

                if ("OK".equals(jsonResult.getString("Success"))) {
                    jsonResult = jsonResult.getJSONObject("Data");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject = jsonResult.getJSONObject("Jobs");
                    Jobss jobss = new Jobss();
                    if (jsonObject.isNull("id")) {
                        jobss.id = null;
                    } else {
                        jobss.id = Integer.parseInt(jsonObject.getString("id"));
                    }

                    if (jsonObject.isNull("job_name")) {
                        jobss.job_name = null;
                    } else {
                        jobss.job_name = jsonObject.getString("job_name");
                    }

                    if (jsonObject.isNull("job_position")) {
                        jobss.job_position = null;
                    } else {
                        jobss.job_position = jsonObject.getString("job_position");
                    }

                    if (jsonObject.isNull("job_addr")) {
                        jobss.job_addr = null;
                    } else {
                        jobss.job_addr = jsonObject.getString("job_addr");
                    }

                    if (jsonObject.isNull("job_num")) {
                        jobss.job_num = null;
                    } else {
                        jobss.job_num = jsonObject.getString("job_num");
                    }

                    if (jsonObject.isNull("job_tel")) {
                        jobss.job_tel = null;
                    } else {
                        jobss.job_tel = jsonObject.getString("job_tel");
                    }

                    if (jsonObject.isNull("job_responsibility")) {
                        jobss.job_responsibility = null;
                    } else {
                        jobss.job_responsibility = jsonObject.getString("job_responsibility");
                    }

                    if (jsonObject.isNull("job_qualification")) {
                        jobss.job_qualification = jsonObject.getString("job_qualification");
                    } else {
                        jobss.job_qualification = jsonObject.getString("job_qualification");
                    }

                    if (jsonObject.isNull("job_group_id")) {
                        jobss.job_group_id = null;
                    } else {
                        jobss.job_group_id = Integer.parseInt(jsonObject.getString("job_group_id"));
                    }

                    if (jsonObject.isNull("student_id")) {
                        jobss.student_id = null;
                    } else {
                        jobss.student_id = jsonObject.getInt("student_id");
                    }

                    if (jsonObject.isNull("job_expiry_date")) {
                        jobss.job_expiry_date = null;
                    } else {
                        jobss.job_expiry_date = jsonObject.getString("job_expiry_date");
                    }

                    if (jsonObject.isNull("created")) {
                        jobss.created = null;
                    } else {
                        jobss.created = jsonObject.getString("created");
                    }

                    jobssList.add(jobss);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            asyncTaskListener.onTaskComplete("searchjobs", jobssList);
        }

        super.onPostExecute(result);
    }
}