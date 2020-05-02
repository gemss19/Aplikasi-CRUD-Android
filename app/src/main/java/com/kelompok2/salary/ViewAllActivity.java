package com.kelompok2.salary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.kelompok2.Conf.Config;
import com.kelompok2.Conf.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    SwipeRefreshLayout refreshLayout;
    LinearLayout layout;
    private ListView listView;
    private String JSON_STRING;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        listView = (ListView) findViewById(R.id.listPgw);
        listView.setOnItemClickListener(this);

        getJSON();

        //Warna refresh
        final SwipeRefreshLayout dorefresh = (SwipeRefreshLayout) findViewById(R.id.swipe);
        dorefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light
        );

        //Event ketika widget dijalankan
        dorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }

            void refreshItem(){
                getJSON();
                onItemLoad();
            }

            void onItemLoad() {
                dorefresh.setRefreshing(false);
            }
        });
    }

    private void showPegawai() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for( int i = 0; i < result.length(); i++ ) {
                JSONObject job = result.getJSONObject(i);
                String id = job.getString(Config.TAG_ID);
                String name = job.getString(Config.TAG_NAMA);
                String desg = job.getString(Config.TAG_POSISI);

                HashMap<String, String> pgw = new HashMap<>();
                pgw.put(Config.TAG_ID, id);
                pgw.put(Config.TAG_NAMA, name);
                pgw.put(Config.TAG_POSISI, desg);
                list.add(pgw);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewAllActivity.this, list, R.layout.listview_pgw,
                new String[]{Config.TAG_ID, Config.TAG_NAMA, Config.TAG_POSISI},
                new int[]{R.id.id, R.id.name, R.id.desg}
        );

        listView.setAdapter(adapter);
    }

    private void getJSON() {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        ViewAllActivity.this, "Mengambil Data", "Harap Tunggu...",
                        false, false
                );
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showPegawai();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler handler = new RequestHandler();
                String s = handler.sendGetRequest(Config.URL_GET_ALL);
                return s;
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewPgwActivity.class);
        HashMap<String, String> map = (HashMap)parent.getItemAtPosition(position);
        intent.putExtra(Config.PGW_ID, map.get(Config.TAG_ID).toString());
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        finishAffinity();
    }

    @Override
    public void onBackPressed(){
        count++;
        if (count >= 1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            finishAffinity();
        } else {
            Toast.makeText(
                    this, "Tekan sekali lagi untuk keluar!", Toast.LENGTH_SHORT
            ).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    count = 0;
                }
            }, 2000);
        }

        super.onBackPressed();
    }
}
