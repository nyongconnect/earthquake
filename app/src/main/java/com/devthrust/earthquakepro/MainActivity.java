package com.devthrust.earthquakepro;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquakes>> {

    private ListView listView;
    QuakeAdapter quakeAdapter;
    TextView textView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv_quakes);
        textView = findViewById(R.id.tv_empty_view);
        progressBar = findViewById(R.id.pb_load_earthquake);
        listView.setEmptyView(textView);
        quakeAdapter = new QuakeAdapter(this,R.layout.earthquake_list,new ArrayList<Earthquakes>());
        listView.setAdapter(quakeAdapter);
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1,null, this).forceLoad();

        }
        else{
            textView.setText("No Network connection");
            progressBar.setVisibility(View.INVISIBLE);
        }









//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String url = "https://earthquake.usgs.gov";
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
//
//            }
//        });

    }

    @NonNull
    @Override
    public Loader<List<Earthquakes>> onCreateLoader(int id, @Nullable Bundle args) {
        return new QuakeAsyncTask(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquakes>> loader, List<Earthquakes> data) {

        quakeAdapter.clear();
        progressBar.setVisibility(View.INVISIBLE);
        textView.setText("No Earthquake Data");
        if(data!= null && !data.isEmpty()){

            quakeAdapter.addAll(data);


        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquakes>> loader) {
        quakeAdapter.clear();

    }


    private static class QuakeAsyncTask extends AsyncTaskLoader<List<Earthquakes>> {


        public QuakeAsyncTask(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Nullable
        @Override
        public List<Earthquakes> loadInBackground() {
            String jsonResponse ="";
            Log.e("do in background", "creating url");
            URL url = QueryUtils.createUrl("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-06-01&endtime=2012-12-01&minmagnitude=6");
            Log.e("do in background", "url created");
            try {
                jsonResponse = QueryUtils.makeHttpResponse(url);
                Log.e("do in background", "make response");
            } catch (IOException e) {
                e.printStackTrace();
            }


            ArrayList<Earthquakes> earthquakes = QueryUtils.extractEarthquakes(jsonResponse);
            return earthquakes;
        }

    }



}
