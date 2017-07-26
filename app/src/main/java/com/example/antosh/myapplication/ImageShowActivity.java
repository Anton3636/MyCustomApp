package com.example.antosh.myapplication;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
public class ImageShowActivity extends AppCompatActivity {

    ImageView iv;
    String pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.profile_image_view);
        iv = (ImageView) findViewById(R.id.iV);
        Intent intent = getIntent();
        pic = intent.getStringExtra("img");

        Picasso.with(getApplicationContext())
                .load(pic)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(iv);
    }
    }

