package com.example.sportybetafinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sportybetafinal.Entities.Evenement;
import com.example.sportybetafinal.Utils.database.AppDataBase;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailsActivity extends AppCompatActivity {
    Button info ;
    private AppDataBase database;
    private Context mContext;

    private SharedPreferences mPreference,sharedPreferencesV;
    public static final String sharedPrefFile = "com.example.sportyfinalbeta";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        sharedPreferencesV = getSharedPreferences("Evenement", Context.MODE_PRIVATE);

        String nom_evanement = sharedPreferencesV.getString("evenement_nom","");
        String location_evenement = sharedPreferencesV.getString("evenement_location","");



        Button info = findViewById(R.id.button7);
        TextView eventtitle = findViewById(R.id.eventtitle);
        TextView eventlocation = findViewById(R.id.eventloc);
        //ImageView homeI = findViewById(R.id.HomeImg);
        //ImageView awayI = findViewById(R.id.AwayImg);
        eventtitle.setText(nom_evanement);
        eventlocation.setText(location_evenement);

       // ImageView awayI = findViewById(R.id.AwayImg);
    // homeN.setText(mPreference.getString("home",""));
      // awayN.setText(mPreference.getString("away",""));
      // homeI.setImageResource(mPreference.getInt("homeI",0));
      // awayI.setImageResource(mPreference.getInt("awayI",0));


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Fragment fragg = new DetailEvent();
                ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.details, fragg).commit();


            }
        });

        try {
            Glide.with(this).load(R.drawable.bg).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


}
