package com.zicure.abacconnect.alumni.search;

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
 * Created by DUMP129 on 11/11/2015.
 */
public class SearchTask extends AsyncTask<String, Void, String> {
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
        if ("searchAlumni".equals(apiCurrent)) {
            List<Alumni> alumniList = new ArrayList<>();

            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);
                jsonResult = jsonResult.getJSONObject("result");
                if ("OK".equals(jsonResult.getString("Success"))) {

                    jsonResult = jsonResult.getJSONObject("Data");
                    jsonResult = jsonResult.getJSONObject("Alumni");
                    JSONObject jsonObjectUser = new JSONObject();

                    Alumni alumni = new Alumni();
                    jsonObjectUser = jsonResult.getJSONObject("User");
                    if (jsonObjectUser.isNull("id")) {
                        alumni.id = null;
                    } else {
                        alumni.id = Integer.parseInt(jsonObjectUser.getString("id"));
                    }

                    if (jsonObjectUser.isNull("user_username")) {
                        alumni.user_username = "";
                    } else {
                        alumni.user_username = jsonObjectUser.getString("user_username");
                    }

                    if (jsonObjectUser.isNull("user_firstname")) {
                        alumni.user_firstname = "";
                    } else {
                        alumni.user_firstname = jsonObjectUser.getString("user_firstname");
                    }

                    if (jsonObjectUser.isNull("user_lastname")) {
                        alumni.user_lastname = "";
                    } else {
                        alumni.user_lastname = jsonObjectUser.getString("user_lastname");
                    }

                    if (jsonObjectUser.isNull("user_email")) {
                        alumni.user_email = "";
                    } else {
                        alumni.user_email = jsonObjectUser.getString("user_email");
                    }


                    JSONArray jsonArrayBusiness = new JSONArray();
                    JSONObject jsonObjectBusiness = new JSONObject();
                    jsonArrayBusiness = jsonResult.getJSONArray("Business");
                    for (int j = 0; j < jsonArrayBusiness.length(); j++) {
                        jsonObjectBusiness = jsonArrayBusiness.getJSONObject(j);
                        if (jsonObjectBusiness.isNull("id")) {
                            alumni.id = null;
                        } else {
                            alumni.id = Integer.parseInt(jsonObjectBusiness.getString("id"));
                        }

                        if (jsonObjectBusiness.isNull("company_name")) {
                            alumni.company_name = "";
                        } else {
                            alumni.company_name = jsonObjectBusiness.getString("company_name");
                        }

                        if (jsonObjectBusiness.isNull("short_description")) {
                            alumni.short_description = "";
                        } else {
                            alumni.short_description = jsonObjectBusiness.getString("short_description");
                        }

                        if (jsonObjectBusiness.isNull("contact_person")) {
                            alumni.contact_person = "";
                        } else {
                            alumni.contact_person = jsonObjectBusiness.getString("contact_person");
                        }

                        if (jsonObjectBusiness.isNull("contact_phone")) {
                            alumni.contact_phone = "";
                        } else {
                            alumni.contact_phone = jsonObjectBusiness.getString("contact_phone");
                        }

                        if (jsonObjectBusiness.isNull("contact_email")) {
                            alumni.contact_email = "";
                        } else {
                            alumni.contact_email = jsonObjectBusiness.getString("contact_email");
                        }

                        if (jsonObjectBusiness.isNull("contact_position")) {
                            alumni.contact_position = null;
                        } else {
                            alumni.contact_position = jsonObjectBusiness.getString("contact_position");
                        }
                    }

                    JSONArray jsonArrayWork = new JSONArray();
                    JSONObject jsonObjectWork = new JSONObject();
                    jsonArrayWork = jsonResult.getJSONArray("Work");
                    for (int k = 0; k < jsonArrayWork.length(); k++) {
                        jsonObjectWork = jsonArrayWork.getJSONObject(k);

                        if (jsonObjectWork.isNull("id")) {
                            alumni.id = null;
                        } else {
                            alumni.id = Integer.parseInt(jsonObjectWork.getString("id"));
                        }

                        if (jsonObjectWork.isNull("work_from")) {
                            alumni.work_from = "";
                        } else {
                            alumni.work_from = jsonObjectWork.getString("work_from");
                        }

                        if (jsonObjectWork.isNull("work_to")) {
                            alumni.work_to = "";
                        } else {
                            alumni.work_to = jsonObjectWork.getString("work_to");
                        }

                        if (jsonObjectWork.isNull("company_name")) {
                            alumni.company_name = "";
                        } else {
                            alumni.company_name = jsonObjectWork.getString("company_name");
                        }

                        if (jsonObjectWork.isNull("work_position")) {

                        } else {
                            alumni.work_position = jsonObjectWork.getString("work_position");
                        }
                    }

                    alumniList.add(alumni);
                }

                asyncTaskListener.onTaskComplete("searchAlumni", alumniList);
            } catch (JSONException e) {
                asyncTaskListener.onTaskComplete("searchAlumni", alumniList);
                e.printStackTrace();
            } catch (Exception e) {
                asyncTaskListener.onTaskComplete("searchAlumni", alumniList);
                Toast.makeText(ApplicationContext.getInstance().getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(result);
        }
    }
}
