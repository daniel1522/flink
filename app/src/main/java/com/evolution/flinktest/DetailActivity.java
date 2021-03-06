package com.evolution.flinktest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView detailCName;
    private TextView detailCStatus;
    private TextView detailCSpecies;
    private TextView detailCType;
    private TextView detailCGender;
    private TextView detailCOrigin;
    private TextView detailCLocation;
    private TextView detailCCreated;
    private ImageView detailCImage;
    private Intent intent;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String origin;
    private String location;
    private String created;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void init() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailCName = findViewById(R.id.c_detail_name);
        detailCStatus = findViewById(R.id.c_detail_status);
        detailCSpecies = findViewById(R.id.c_detail_species);
        detailCType = findViewById(R.id.c_detail_type);
        detailCGender = findViewById(R.id.c_detail_gender);
        detailCOrigin = findViewById(R.id.c_detail_origin);
        detailCLocation = findViewById(R.id.c_detail_location);
        detailCCreated = findViewById(R.id.c_detail_created);
        detailCImage = findViewById(R.id.c_image_detail);
        intent = getIntent();
        name = intent.getStringExtra("name");
        status = intent.getStringExtra("status");
        species = intent.getStringExtra("species");
        type = intent.getStringExtra("type");
        gender = intent.getStringExtra("gender");
        origin = intent.getStringExtra("origin");
        location = intent.getStringExtra("location");
        created = intent.getStringExtra("created");
        image = intent.getStringExtra("image");
        detailCName.setText(name);
        detailCStatus.setText(status);
        detailCSpecies.setText(species);
        detailCType.setText(type);
        detailCGender.setText(gender);
        detailCOrigin.setText(origin);
        detailCLocation.setText(location);
        detailCCreated.setText(created);
        Picasso.get().load(image).into(detailCImage);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }
}
