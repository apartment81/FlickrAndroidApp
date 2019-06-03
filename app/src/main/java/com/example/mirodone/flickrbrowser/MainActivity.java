package com.example.mirodone.flickrbrowser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetFlickrJsonData.OnDataAvailable {

    private static final String TAG = "MainActivity";
    private FlickRecyclerViewAdapter mFlickRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: starts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFlickRecyclerViewAdapter = new FlickRecyclerViewAdapter(this, new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickRecyclerViewAdapter);

        
//        GetRawData getRawData = new GetRawData(this);
//        getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=hotwheels,ferrari&tagmode=any&format=json&nojsoncallback=1");


        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();
        GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);

        getFlickrJsonData.execute("ferrari, mercedes");
        Log.d(TAG, "onResume Ends");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected() returned: returned ");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable() : starts ");

        if (status == DownloadStatus.OK) {
            mFlickRecyclerViewAdapter.loadNewData(data);

        } else {
            //download or processing failed
            Log.e(TAG, "onDownloadComplete() failed with status " + status);
        }
        Log.d(TAG, "onDataAvailable() : ends ");
    }


}
