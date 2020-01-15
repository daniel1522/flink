package com.evolution.flinktest.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.evolution.flinktest.adapter.CharacterAdapter;
import com.evolution.flinktest.model.Character;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.evolution.flinktest.MainActivity.cAdapter;
import static com.evolution.flinktest.MainActivity.cRecyclerView;
import static com.evolution.flinktest.MainActivity.characters;
import static com.evolution.flinktest.MainActivity.errorImage;
import static com.evolution.flinktest.MainActivity.noNetTxt;

public class CharactersAsyncTask extends AsyncTask<String, Integer, Boolean> {

    private Context context;
    private String serverResponse = null;
    private ProgressDialog dialog;

    public CharactersAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        cRecyclerView.setAdapter(null);
        cRecyclerView.setVisibility(View.VISIBLE);
        noNetTxt.setVisibility(View.GONE);
        dialog = ProgressDialog.show(context, "",
                "Cargando...", true);
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String url = "https://rickandmortyapi.com/api/character/";
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

        dialog.dismiss();

        if (serverResponse != null) {

            characters = new ArrayList<>();

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

            } catch (JSONException e) {
                e.printStackTrace();
            }

            cAdapter = new CharacterAdapter(context, characters);
            cRecyclerView.setAdapter(cAdapter);
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(context);
            cRecyclerView.setLayoutManager(layoutManager);
            cRecyclerView.setHasFixedSize(true);
            //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cRecyclerView.getContext(),
            //        layoutManager.getOrientation());
            //cRecyclerView.addItemDecoration(dividerItemDecoration);
        } else {
            //Toast.makeText(context, "Error al obtener datos del servidor", Toast.LENGTH_LONG).show();
            cRecyclerView.setVisibility(View.GONE);
            errorImage.setVisibility(View.VISIBLE);
            noNetTxt.setVisibility(View.VISIBLE);
        }
    }
}