package com.kbrtz.zodiacmatch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kbrtz.zodiacmatch.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private Bundle informationBundle = new Bundle();
    private TextView tvName, tvSign, tvAge, tvBio, tvShowingSing;
    private Button btSelectSing, btSaveConfig, btChangeBio;
    private EditText etBio;
    private ImageView ivProfilePic;
    private DatabaseReference mDatabase;
    private User currentUser;
    private String userId;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        currentUser = new User();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        tvName = (TextView) findViewById(R.id.tv_name);
        tvSign = (TextView) findViewById(R.id.tv_sign);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvBio = (TextView) findViewById(R.id.tv_bio);
        tvShowingSing = (TextView) findViewById(R.id.tv_showing_sing);
        btSelectSing = (Button) findViewById(R.id.bt_select_sing);
        btSaveConfig = (Button) findViewById(R.id.bt_save_config);
        btChangeBio = (Button) findViewById(R.id.bt_change_bio);
        ivProfilePic = (ImageView) findViewById(R.id.iv_profile_pic);
        etBio = (EditText) findViewById(R.id.et_bio);

        informationBundle = getIntent().getExtras();

        if (informationBundle != null) {

            if (informationBundle.getString(Constants.PROFILE_PIC) != null) {
                Log.e("profile", "profile pic: " + informationBundle.getString(Constants.PROFILE_PIC));
                Picasso.with(getApplicationContext()).load(informationBundle.getString(Constants.PROFILE_PIC)).into(ivProfilePic);

                currentUser.setProfilePic(informationBundle.getString(Constants.PROFILE_PIC));
            }

            if (informationBundle.getString(Constants.FIRST_NAME) != null) {
                String name = informationBundle.getString(Constants.FIRST_NAME);

                currentUser.setFirstName(informationBundle.getString(Constants.FIRST_NAME));

                if (informationBundle.getString(Constants.LAST_NAME) != null) {
                    name = name + " " + informationBundle.getString(Constants.LAST_NAME);

                    currentUser.setLastName(informationBundle.getString(Constants.LAST_NAME));

                }
                tvName.setText(name);
            }

            if (informationBundle.getString(Constants.BIRTHDAY) != null) {
                currentUser.setBirthday(informationBundle.getString(Constants.BIRTHDAY));
                currentUser.setSign(getString(CalculationsUtils.calculateSign(informationBundle.getString(Constants.BIRTHDAY))));
                currentUser.setAge((CalculationsUtils.calculateAge(informationBundle.getString(Constants.BIRTHDAY))) + "");

                tvAge.setText(currentUser.getAge());
                tvSign.setText(currentUser.getSign());
            }

            if (informationBundle.getString(Constants.EMAIL) != null) {
                currentUser.setEmail(informationBundle.getString(Constants.EMAIL));
            }

            if (informationBundle.getString(Constants.GENDER) != null) {
                currentUser.setGender(informationBundle.getString(Constants.GENDER));
            }

            if (informationBundle.getString(Constants.FACEBOOK_ID) != null) {
                userId = informationBundle.getString(Constants.FACEBOOK_ID);
            }

        }

        btChangeBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvBio.getVisibility() == View.VISIBLE) {
                    tvBio.setVisibility(View.GONE);
                    etBio.setVisibility(View.VISIBLE);
                    btChangeBio.setText(R.string.done_btn);
                    etBio.setText(tvBio.getText());
                } else {
                    tvBio.setVisibility(View.VISIBLE);
                    etBio.setVisibility(View.GONE);
                    btChangeBio.setText(R.string.bio_btn);
                    tvBio.setText(etBio.getText());
                }
            }
        });

        btSelectSing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList seletedItems = new ArrayList();

                AlertDialog dialog = new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle(getString(R.string.sign_dialog_title))
                        .setMultiChoiceItems(getZodiacsNames(), null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                tvShowingSing.setText(getSelectedSigns(seletedItems));
                                currentUser.setPreferedSigns(getSelectedSigns(seletedItems));
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        btSaveConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setBio(tvBio.getText().toString());
                saveNewUser();
            }
        });

    }

    private CharSequence[] getZodiacsNames() {
        CharSequence[] items;
        List<String> auxList = new ArrayList<>();
        List<Zodiacs> zodiacsList = Arrays.asList(Zodiacs.values());

        for (Zodiacs z : zodiacsList) {
            auxList.add(getString(z.getZodiacName()));
        }

        items = auxList.toArray(new CharSequence[auxList.size()]);

        return items;

    }

    private String getSelectedSigns(ArrayList seletedItems) {

        List<Zodiacs> zodiacsList = Arrays.asList(Zodiacs.values());
        List<String> selectedSigns = new ArrayList<>();

        for (Object o: seletedItems) {
            selectedSigns.add(getString(zodiacsList.get((Integer)o).getZodiacName()));
        }

        StringBuilder builder = new StringBuilder();
        for(String s : selectedSigns) {
            builder.append(s + ", ");
        }
        return builder.toString();
    }

    private void saveNewUser() {

        mDatabase.child("users").child(userId).setValue(currentUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("firebase", "Data could not be saved. " + databaseError.getMessage());
                } else {
                    Log.e("firebase", "Data saved successfully.");
                }
            }
        });
    }



}
