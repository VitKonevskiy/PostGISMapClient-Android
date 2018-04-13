package nngu.konevsky.diplomapp.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import nngu.konevsky.diplomapp.App;
import nngu.konevsky.diplomapp.CustomMap;
import nngu.konevsky.diplomapp.DrawView;
import nngu.konevsky.diplomapp.R;
import nngu.konevsky.diplomapp.pojo.Line;
import nngu.konevsky.diplomapp.pojo.Point;
import nngu.konevsky.diplomapp.pojo.Pojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    private Pojo pojo;
    private final String LOG_TAG = "MYTAG";
    private DrawView drawView;
    private boolean allowDownloading = false;
    private int objectsCount = 0;
    private ProgressBar progressBar;
    private Callback<Pojo> pojoResponse;

    private CustomMap myMap;

    private final int partSize = 1000;
    private int neededPartRequest = 0;
    private int currPartCounter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myMap = new CustomMap();
        drawView = new DrawView(this,myMap);
        setContentView(R.layout.activity_map);
        addContentView(drawView, new ActionBar.LayoutParams(1080,1920));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        pojoResponse = new Callback<Pojo>() {
            @Override
            public synchronized void onResponse(Call<Pojo> call, Response<Pojo> response) {

                if (response.body().rootObject.point != null)
                    myMap.points.add(response.body().rootObject.point);
                if (response.body().rootObject.line != null)
                    myMap.lines.add(response.body().rootObject.line);



                if (response.body().rootObject.itemCount == (currPartCounter * partSize - 1)
                        && currPartCounter < neededPartRequest && allowDownloading) {
                    for (int i = currPartCounter * partSize; i < partSize * (currPartCounter + 1); i++) {
                        String url = "nn;" + i + ";";
                        App.getApi().getDatabaseObject(url).enqueue(this);
                    }
                    currPartCounter++;
                }
                if (currPartCounter == neededPartRequest && allowDownloading) {
                    for (int i = currPartCounter * partSize; i < objectsCount; i++) {
                        String url = "nn;" + i + ";";
                        App.getApi().getDatabaseObject(url).enqueue(this);
                    }

                }
                if ((response.body().rootObject.itemCount) % 100 == 0 && allowDownloading) {
                    Log.d(LOG_TAG, "Print! item = " + response.body().rootObject.itemCount);
                    final int progress = (response.body().rootObject.itemCount * 100 / (objectsCount-1));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            drawView.allowPaint = true;
                            Log.d(LOG_TAG, "Print! progress = " + progress);
                            progressBar.setProgress(progress);
                            drawView.invalidate();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d(LOG_TAG, "FAILED!");
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.map_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case (R.id.map_activity_download_status):
                if (allowDownloading)
                {
                    //Stop button pressed!
                    item.setIcon(R.drawable.play);
                    allowDownloading = false;
                }
                else
                {
                    //Start button pressed!
                    myMap.clear();
                    drawView.invalidate();
                    Log.d(LOG_TAG,"START request");
                    App.getApi().getDatabaseObject("nn;").enqueue(new Callback<Pojo>() {
                        @Override
                        public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                            if (response.body()!=null) {
                                pojo = response.body();
                                Log.d(LOG_TAG, "Items = " + pojo.rootObject.itemCount);
                                objectsCount = pojo.rootObject.itemCount;
                                Log.d(LOG_TAG, "Xmin = " + pojo.rootObject.xMin);
                                Log.d(LOG_TAG, "Ymin = " + pojo.rootObject.yMin);

                                //Set start coordinates for CustomMap
                                myMap.xStart = pojo.rootObject.xMin;
                                myMap.yStart = pojo.rootObject.yMin;

                                String test =  pojo.rootObject.point == null ? "null" : String.valueOf(pojo.rootObject.point.x);
                                String test2 =  pojo.rootObject.line == null ? "null" : String.valueOf(pojo.rootObject.line.points.get(0).x);
                                Log.d(LOG_TAG, "Point = " + test);
                                Log.d(LOG_TAG, "Line = " + test2);


                                //Sending requests by part (1000 in one)
                                if (objectsCount > partSize)
                                    neededPartRequest = objectsCount / 1000 + 1;
                                for(int i=0;i<partSize;i++) {//
                                    String url = "nn;" + i + ";";
                                    App.getApi().getDatabaseObject(url).enqueue(pojoResponse);
                                }
                            }
                            else
                                Log.d(LOG_TAG,"Empty body");
                            Log.d(LOG_TAG,"END response");
                        }

                        @Override
                        public void onFailure(Call<Pojo> call, Throwable t) {
                            Log.d(LOG_TAG,"FAILED");
                            t.printStackTrace();
                        }
                    });
                    Log.d(LOG_TAG,"END request");
                    item.setIcon(R.drawable.stop);
                    //item.getSubMenu().getItem(1).setIcon(R.drawable.stop);
                    allowDownloading = true;
                }
                result = true;
                //invalidateOptionsMenu();
                break;

            case (R.id.map_activity_info):
                Toast.makeText(this, "Info!", Toast.LENGTH_LONG).show();
                result = true;
                break;
        }
        return result;
    }
}
