package nngu.konevsky.diplomapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import nngu.konevsky.diplomapp.App;
import nngu.konevsky.diplomapp.R;
import nngu.konevsky.diplomapp.pojo.Pojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MYTAG";

    private TextView mainText;
    private Button toMapButton;
    private Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainText = (TextView)findViewById(R.id.textId);
        toMapButton = (Button) findViewById(R.id.toMapButton);
        getButton = (Button) findViewById(R.id.getButton);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        toMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mainText.setText("Lines = " + pojo.rootObject.lines.size() +"; Points = " + pojo.rootObject.points.size());
            }
        });
    }
}
