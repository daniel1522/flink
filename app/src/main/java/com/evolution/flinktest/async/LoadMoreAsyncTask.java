package com.evolution.flinktest.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.evolution.flinktest.model.Character;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.evolution.flinktest.MainActivity.cAdapter;
import static com.evolution.flinktest.MainActivity.characters;
import static com.evolution.flinktest.MainActivity.currentUrl;
import static com.evolution.flinktest.MainActivity.loading;

public class LoadMoreAsyncTask extends AsyncTask<String, Integer, Boolean> {

    private Context context;
    private String serverResponse = null;

    public LoadMoreAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Log.v("LMAT", "Loading more...");
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String url = params[0];
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            serverResponse = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if (serverResponse != null) {
            try {
                JSONObject response = new JSONObject(serverResponse);

                JSONArray charList = response.getJSONArray("results");
                for (int i = 0; i < charList.length(); i++) {

                    JSONObject characterInfo = charList.getJSONObject(i);
                    characters.add(new Character(characterInfo.getInt("id"),
                            characterInfo.getString("name"),
                            characterInfo.getString("status"),
                            characterInfo.getString("species"),
                            characterInfo.getString("type"),
                            characterInfo.getString("gender"),
                            characterInfo.getJSONObject("origin").getString("name"),
                            characterInfo.getJSONObject("location").getString("name"),
                            characterInfo.getString("image"),
                            characterInfo.getString("created")));
                }

                JSONObject info = response.getJSONObject("info");
                currentUrl = info.getString("next");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            cAdapter.notifyDataSetChanged();
            loading = true;
        }
    }
}