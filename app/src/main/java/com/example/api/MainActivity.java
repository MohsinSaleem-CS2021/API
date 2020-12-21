package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private ProgressBar progressBar;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progressBar);

        queue = Volley.newRequestQueue(this);

        fetchJsonResponse();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (count < 10){
                    count++;
                    int finalCount = count;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("mohsin", "run: ");
                            text.setText(finalCount +"");
                            progressBar.setProgress(finalCount);
                        }
                    });
                }

            }
        });

        thread.start();
    }

    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://run.mocky.io/v3/27a10eb7-9a0d-4462-b53b-3dea1f1c3d5a", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("mohsin", "onResponse: "+response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("mohsin", "onErrorResponse: "+ error.getMessage());
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        queue.add(req);
    }
}