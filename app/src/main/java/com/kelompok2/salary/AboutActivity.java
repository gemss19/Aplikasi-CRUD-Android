package com.kelompok2.salary;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.ViewCompat;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends AppCompatActivity {

    private Integer[] mTeamIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.aboutToolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpTeamItems();
    }

    private void setUpTeamItems() {
        String[] textItems = getResources().getStringArray(R.array.about_team_names);
        mTeamIds = new Integer[textItems.length];
        TypedArray typedArray = getResources().obtainTypedArray(R.array.about_team_drawables);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.aboutTeamContainer);
        assert linearLayout != null;
        for (int i = 0; i < textItems.length; i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.about_people_item, linearLayout, false);
            ((CircleImageView) itemView.findViewById(R.id.aboutPeopleIcon)).setImageResource(typedArray.getResourceId(i, -1));
            ((TextView) itemView.findViewById(R.id.aboutPeopleText)).setText(textItems[i]);
            int id = ViewCompat.generateViewId();
            itemView.setId(id);
            mTeamIds[i] = id;
            linearLayout.addView(itemView, i);
        }
        typedArray.recycle();
    }

    public void onClick(View view) {
        Integer id = view.getId();
        List<String> linklist;
        String url = null;
        List teamIds = Arrays.asList(mTeamIds);
        if (teamIds.contains(id)) {
            linklist = Arrays.asList(getResources().getStringArray(R.array.about_team_links));
            url = linklist.get(teamIds.indexOf(id));
        }

        if ( url != null ) {
            openURL(url);
        } else {
            Toast.makeText(AboutActivity.this, "Invalid URL", Toast.LENGTH_SHORT).show();
        }
    }

    private void openURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private int count = 0;

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
