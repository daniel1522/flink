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

        detailCName = (TextView) findViewById(R.id.c_detail_name);
        detailCStatus = (TextView) findViewById(R.id.c_detail_status);
        detailCSpecies = (TextView) findViewById(R.id.c_detail_species);
        detailCType = (TextView) findViewById(R.id.c_detail_type);
        detailCGender = (TextView) findViewById(R.id.c_detail_gender);
        detailCOrigin = (TextView) findViewById(R.id.c_detail_origin);
        detailCLocation = (TextView) findViewById(R.id.c_detail_location);
        detailCCreated = (TextView) findViewById(R.id.c_detail_created);
        detailCImage = (ImageView) findViewById(R.id.c_image_detail);

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

        detailCName.setText(getResources().getString(R.string.name) + " " + name);
        detailCStatus.setText(getResources().getString(R.string.status) + " " + status);
        detailCSpecies.setText(getResources().getString(R.string.species) + " " + species);
        detailCType.setText(getResources().getString(R.string.type) + " " + type);
        detailCGender.setText(getResources().getString(R.string.gender) + " " + gender);
        detailCOrigin.setText(getResources().getString(R.string.origin) + " " + origin);
        detailCLocation.setText(getResources().getString(R.string.location) + " " + location);
        detailCCreated.setText(getResources().getString(R.string.created) + " " + created);
        Picasso.get().load(image).into(detailCImage);
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
