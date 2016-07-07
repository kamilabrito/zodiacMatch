package com.kbrtz.zodiacmatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private Bundle informationBundle = new Bundle();
    private TextView tvName, tvSign, tvAge, tvBio, tvShowingSing;
    private Button btSelectSing, btSaveConfig;
    private ImageView ivProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvSign = (TextView) findViewById(R.id.tv_sign);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvBio = (TextView) findViewById(R.id.tv_bio);
        tvShowingSing = (TextView) findViewById(R.id.tv_showing_sing);
        btSelectSing = (Button) findViewById(R.id.bt_select_sing);
        btSaveConfig = (Button) findViewById(R.id.bt_save_config);
        ivProfilePic = (ImageView) findViewById(R.id.iv_profile_pic);

        informationBundle = getIntent().getExtras();

        if (informationBundle != null) {

            if (informationBundle.getString(Constants.PROFILE_PIC) != null) {
                Log.e("profile", "profile pic: " + informationBundle.getString(Constants.PROFILE_PIC));
                Picasso.with(getApplicationContext()).load(informationBundle.getString(Constants.PROFILE_PIC)).into(ivProfilePic);
            }

            if (informationBundle.getString(Constants.FIRST_NAME) != null) {
                String name = informationBundle.getString(Constants.FIRST_NAME);
                    if(informationBundle.getString(Constants.LAST_NAME) != null){
                        name = name + " " + informationBundle.getString(Constants.LAST_NAME);
                    }
                tvName.setText(name);
            }

            if (informationBundle.getString(Constants.BIRTHDAY) != null) {
                tvAge.setText(CalculationsUtils.calculateAge(informationBundle.getString(Constants.BIRTHDAY)) + "");
                tvSign.setText(CalculationsUtils.calculateSign(informationBundle.getString(Constants.BIRTHDAY)));
            }

        }

    }
}
