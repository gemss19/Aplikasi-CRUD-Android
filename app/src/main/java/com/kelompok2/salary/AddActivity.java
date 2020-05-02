package com.kelompok2.salary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelompok2.Conf.Config;
import com.kelompok2.Conf.RequestHandler;

import java.util.HashMap;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler mHandler = new Handler();

    //Mendefinisikan View
    private EditText editNama;
    private EditText editPosisi;
    private EditText editGaji;

    private Button btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //inisialisasi dari view
        editNama = (EditText) findViewById(R.id.addNama);
        editPosisi = (EditText) findViewById(R.id.addPosisi);
        editGaji = (EditText) findViewById(R.id.addGaji);

        btAdd = (Button) findViewById(R.id.btAdd);

        //Setting listener to button
        btAdd.setOnClickListener(this);
    }

    //Method membuat pegawai
    private void addPegawai() {

        final String name = editNama.getText().toString().trim();
        final String desg = editPosisi.getText().toString().trim();
        final String sal = editGaji.getText().toString().trim();

        class AddPegawai extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(
                        AddActivity.this, "Menambahkan...", "Tunggu...",
                        false, false
                );
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        AddActivity.this, s, Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_PGW_NAMA, name);
                params.put(Config.KEY_PGW_POSISI, desg);
                params.put(Config.KEY_PGW_GAJI, sal);

                RequestHandler handler = new RequestHandler();
                String res = handler.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        if( TextUtils.isEmpty(name) ||  TextUtils.isEmpty(desg) ||  TextUtils.isEmpty(sal) ) {
            Toast.makeText(
                    this, "Tidak Boleh Kosong!", Toast.LENGTH_SHORT
            ).show();
        } else {
            AddPegawai ap = new AddPegawai();
            ap.execute();

            editNama.setText("");
            editPosisi.setText("");
            editGaji.setText("");
        }

    }

    @Override
    public void onClick(View view) {
        if ( view == btAdd ) {
            addPegawai();
            mHandler.postDelayed(mUpdateTimeTask, 100);
        }
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(
                    AddActivity.this, MainActivity.class
            ));
        }
    };

    private int count = 0;

    @Override
    public void onBackPressed(){
        count++;
        if (count >= 1) {
            Intent intent = new Intent(this, MainActivity.class);
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
