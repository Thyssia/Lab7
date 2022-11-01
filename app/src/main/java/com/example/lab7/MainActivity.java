package com.example.lab7;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    public static String name;
    public static String height;
    public static String mass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        JsonWork jWork = new JsonWork();
        jWork.execute();
    }

    private class JsonWork extends AsyncTask<String,String, List<JsonModel>>{

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String  FullJsonData;
        @Override
        protected List<JsonModel> doInBackground(String... strings) {

            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder= new StringBuilder();
                String line;

                while ((line= bufferedReader.readLine())!= null){
                    stringBuilder.append(line);
                }
                FullJsonData =  stringBuilder.toString();
                List<JsonModel> jsonModelList = new ArrayList<>();

                JSONObject jsonStartingObject = new JSONObject(FullJsonData);
                JSONArray  jsonSWArray = jsonStartingObject.getJSONArray("results");

                for(int i=0; i<jsonSWArray.length(); i++) {

                    JSONObject jsonUnderArrayObject = jsonSWArray.getJSONObject(i);

                    JsonModel jsonModel = new JsonModel();
                    jsonModel.setName(jsonUnderArrayObject.getString("name"));
                    jsonModel.setHeight(jsonUnderArrayObject.getString("height"));
                    jsonModel.setMass(jsonUnderArrayObject.getString("mass"));
                    jsonModelList.add(jsonModel);
                }
                return jsonModelList;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally{

                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<JsonModel> jsonModels) {
            super.onPostExecute(jsonModels);
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(), jsonModels);
            listView.setAdapter(adapter);
            boolean isTablet = findViewById(R.id.fragmentLocation) != null;
            listView.setOnItemClickListener((list, item, position, id) -> {
                name = jsonModels.get(position).getName();
                height = jsonModels.get(position).getHeight();
                mass = jsonModels.get(position).getMass();

                Bundle dataToPass = new Bundle();
                dataToPass.putString("name", name);
                dataToPass.putString("height", height);
                dataToPass.putString("mass", mass);

                if(isTablet)
                {
                    DetailsFragment dFragment = new DetailsFragment();
                    dFragment.setArguments(dataToPass);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentLocation, dFragment)
                            .commit();
                }
                else //isPhone
                {
                    Intent nextActivity = new Intent(MainActivity.this, EmptyActivity.class);
                    nextActivity.putExtras(dataToPass);
                    startActivity(nextActivity);
                }
            });
        }
    }
}