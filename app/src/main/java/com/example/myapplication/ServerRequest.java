package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.http.HttpsConnection;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ServerRequest {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIME = 10000 * 15;
    public static final String SERVER_ADDRESS = "https://project99492038.000webhostapp.com/";

    public ServerRequest(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Пожалуйста, подождите...");
    }

    public void getEventsSchedule(User user, GetUserCallbackArray callback) {
        progressDialog.show();
        new GetEventsSchedule(callback).execute();
    }

    public void AddEventToSchedule(User user, String eventTitle, GetUserCallback callback) {
        progressDialog.show();
        new AddEventToSchedule(user, eventTitle, callback).execute();
    }

    public void storeUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, callback).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, callback).execute();
    }

    public void getDaySchedule(User user, String date, GetUserCallbackArray callback){
        progressDialog.show();
        new GetDaySchedule(user, callback, date).execute();
    }

    public void getSingleEventSchedule(String eventTitle, GetUserCallbackArray callback){
        progressDialog.show();
        new GetSingleEventSchedule(eventTitle, callback).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallback callback;

        public StoreUserDataAsyncTask(User user, GetUserCallback callback) {
            this.user = user;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("email", user.email));

            HttpParams httpsRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpsRequestParams, CONNECTION_TIME);
            HttpConnectionParams.setSoTimeout(httpsRequestParams, CONNECTION_TIME);

            HttpClient client = new DefaultHttpClient(httpsRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.dismiss();
            callback.done(null);

            super.onPostExecute(aVoid);
        }
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallback callback;

        public fetchUserDataAsyncTask(User user, GetUserCallback callback) {
            this.user = user;
            this.callback = callback;
        }

        @Override
        protected void onPostExecute(User returnedUser) {

            progressDialog.dismiss();
            callback.done(returnedUser);

            super.onPostExecute(returnedUser);
        }

        @Override
        protected User doInBackground(Void... voids) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpsRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpsRequestParams, CONNECTION_TIME);
            HttpConnectionParams.setSoTimeout(httpsRequestParams, CONNECTION_TIME);

            HttpClient client = new DefaultHttpClient(httpsRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                Log.e("JSONParser", result);
                //result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1)
                JSONObject jsonObject = new JSONObject(result);
                //Log.e("JSONParser", result);

                if(jsonObject.length() == 0)
                    returnedUser = null;
                else {
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    String password = jsonObject.getString("password");
                    String username = jsonObject.getString("username");

                    returnedUser = new User(username, name, password, email);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            return returnedUser;
        }
    }

    public class GetDaySchedule extends AsyncTask<Void, Void, ArrayList>
    {
        User user;
        GetUserCallbackArray callback;
        String date;
        ArrayList arrayList;

        public GetDaySchedule(User user, GetUserCallbackArray callback, String date){
            this.user = user;
            this.callback = callback;
            this.date = date;
            arrayList = new ArrayList();
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("date", date));

            HttpParams httpsRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpsRequestParams, CONNECTION_TIME);
            HttpConnectionParams.setSoTimeout(httpsRequestParams, CONNECTION_TIME);

            HttpClient client = new DefaultHttpClient(httpsRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "GetDaySchedule.php");




            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                Log.e("JSONParser", result);
                //result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1)
                //JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(result);


                if(jsonArray.length() == 0)
                    return null;
                else {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        String temp = jsonArray.getString(i);
                        String desc = new JSONObject(temp).getString("description");
                        String title = new JSONObject(temp).getString("title");
                        arrayList.add(new String[]{title, desc});
                        Log.e("bb",desc);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return arrayList;
        }


        @Override
        protected void onPostExecute(ArrayList arrayList) {

            progressDialog.dismiss();
            callback.done(arrayList);

            super.onPostExecute(arrayList);
        }
    }

    public class GetEventsSchedule extends AsyncTask<Void, Void, ArrayList>
    {
        GetUserCallbackArray callback;
        ArrayList arrayList;

        public GetEventsSchedule(GetUserCallbackArray callback){
            this.callback = callback;
            arrayList = new ArrayList();
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            HttpParams httpsRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpsRequestParams, CONNECTION_TIME);
            HttpConnectionParams.setSoTimeout(httpsRequestParams, CONNECTION_TIME);

            HttpClient client = new DefaultHttpClient(httpsRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "GetEventsSchedule.php");




            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                Log.e("JSONParser", result);
                //result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1)
                //JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(result);


                if(jsonArray.length() == 0)
                    return null;
                else {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        String temp = jsonArray.getString(i);
                        String desc = new JSONObject(temp).getString("description");
                        String title = new JSONObject(temp).getString("title");
                        String startDate = new JSONObject(temp).getString("start_date");
                        String endDate = new JSONObject(temp).getString("end_date");
                        arrayList.add(new String[]{title, desc, startDate, endDate});
                        Log.e("bb",desc);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return arrayList;
        }


        @Override
        protected void onPostExecute(ArrayList arrayList) {

            progressDialog.dismiss();
            callback.done(arrayList);

            super.onPostExecute(arrayList);
        }
    }

    public class GetSingleEventSchedule extends AsyncTask<Void, Void, ArrayList>
    {
        GetUserCallbackArray callback;
        ArrayList arrayList;
        String eventTitle;

        public GetSingleEventSchedule(String eventTitle,GetUserCallbackArray callback){
            this.callback = callback;
            this.eventTitle = eventTitle;

            arrayList = new ArrayList();
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("title", eventTitle));


            HttpParams httpsRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpsRequestParams, CONNECTION_TIME);
            HttpConnectionParams.setSoTimeout(httpsRequestParams, CONNECTION_TIME);

            HttpClient client = new DefaultHttpClient(httpsRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "GetSingleEventSchedule.php");




            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                Log.e("JSONParser", result);
                //result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1)
                //JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(result);


                if(jsonArray.length() == 0)
                    return null;
                else {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        String temp = jsonArray.getString(i);
                        String desc = new JSONObject(temp).getString("description");
                        String title = new JSONObject(temp).getString("title");
                        String startDate = new JSONObject(temp).getString("date");
                        arrayList.add(new String[]{title, desc, startDate});
                        Log.e("bb",desc);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return arrayList;
        }


        @Override
        protected void onPostExecute(ArrayList arrayList) {

            progressDialog.dismiss();
            callback.done(arrayList);

            super.onPostExecute(arrayList);
        }
    }

    public class AddEventToSchedule extends AsyncTask<Void, Void, Void>
    {
        GetUserCallback callback;
        String eventTitle;
        User user;

        public AddEventToSchedule(User user, String eventTitle,GetUserCallback callback){
            this.callback = callback;
            this.eventTitle = eventTitle;
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("title", eventTitle));
            dataToSend.add(new BasicNameValuePair("username", user.username));



            HttpParams httpsRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpsRequestParams, CONNECTION_TIME);
            HttpConnectionParams.setSoTimeout(httpsRequestParams, CONNECTION_TIME);

            HttpClient client = new DefaultHttpClient(httpsRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "AddEventToSchedule.php");




            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void Void) {

            progressDialog.dismiss();
            callback.done(null);

            super.onPostExecute(Void);
        }
    }
}
