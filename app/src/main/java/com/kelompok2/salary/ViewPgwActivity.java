package com.kelompok2.salary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelompok2.Conf.Config;
import com.kelompok2.Conf.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ViewPgwActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editId;
    private EditText editName;
    private EditText editDesg;
    private EditText editSal;

    private Button btUpdate;
    private Button btDelete;

    private Handler mHandler = new Handler();
    private String id;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pgw);

        editId = (EditText) findViewById(R.id.editId);
        editName = (EditText) findViewById(R.id.editNama);
        editDesg = (EditText) findViewById(R.id.editPosisi);
        editSal = (EditText) findViewById(R.id.editGaji);

        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);

        btUpdate.setOnClickListener(this);
        btDelete.setOnClickListener(this);

        id = getIntent().getStringExtra(Config.PGW_ID);
        editId.setText(id);
        getPegawai();
    }

    private void getPegawai() {
        class GetPegawai extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(
                        ViewPgwActivity.this, "Memuat..", "Harap Tunggu..",
                        false, false
                );
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                showPegawai(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler handler = new RequestHandler();
                return handler.sendGetRequestParam(Config.URL_GET_PGW, id);
            }
        }

        GetPegawai gp = new GetPegawai();
        gp.execute();
    }

    private void showPegawai(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            if(result.length() > 0) {
                JSONObject c = result.getJSONObject(0);

                String name = c.getString(Config.TAG_NAMA);
                String desg = c.getString(Config.TAG_POSISI);
                String sal = c.getString(Config.TAG_GAJI);

                editName.setText(name);
                editDesg.setText(desg);
                editSal.setText(sal);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updatePegawai(){

        final String name = editName.getText().toString().trim();
        final String desg = editDesg.getText().toString().trim();
        final String sal = editSal.getText().toString().trim();

        class UpdatePegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(
                        ViewPgwActivity.this, "Updating...", "Please Wait...",
                        false, false
                );
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        ViewPgwActivity.this, s, Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_PGW_ID, id);
                hashMap.put(Config.KEY_PGW_NAMA, name);
                hashMap.put(Config.KEY_PGW_POSISI, desg);
                hashMap.put(Config.KEY_PGW_GAJI, sal);

                RequestHandler handler = new RequestHandler();
                return handler.sendPostRequest(Config.URL_UPDATE_PGW, hashMap);
            }
        }
        UpdatePegawai up = new UpdatePegawai();
        up.execute();
    }

    private void deletePegawai(){
        class DeletePegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(
                        ViewPgwActivity.this, "Deleting", "Please Wait...",
                        false, false
                );
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        ViewPgwActivity.this, s, Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler handler = new RequestHandler();
                return handler.sendGetRequestParam(Config.URL_DELETE_PGW, id);
            }
        }

        DeletePegawai dp = new DeletePegawai();
        dp.execute();
    }

    private void confirmDelete(){


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Apakah kamu yakin akan menghapus pegawai ini?");

        alertBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePegawai();
                        startActivity(new Intent(ViewPgwActivity.this, ViewAllActivity.class));
                    }
                });

        alertBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(
                    ViewPgwActivity.this, ViewAllActivity.class
            ));
        }
    };

    @Override
    public void onClick(View v) {

        if(v == btUpdate){
            updatePegawai();
            mHandler.postDelayed(mUpdateTimeTask, 100);
        }

        if (v == btDelete){
            confirmDelete();
        }

    }

    @Override
    public void onBackPressed(){
        count++;
        if (count >= 1) {
            Intent intent = new Intent(this, ViewAllActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
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
