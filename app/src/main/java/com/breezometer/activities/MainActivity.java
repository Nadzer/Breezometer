package com.breezometer.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.breezometer.BreezoMeter;
import com.breezometer.R;
import com.breezometer.adapters.ListAdapter;
import com.breezometer.entities.BreezoMeterEntity;
import com.breezometer.interfaces.ResultListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ResultListener {
    private EditText mSearchCity;
    private TextView mCountry;
    private TextView mAqi;
    private TextView mBreezoAqi;
//    private FrameLayout frameLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo);

//        startDefaultIntro();

        mAqi = (TextView) findViewById(R.id.aqi);
        mBreezoAqi = (TextView) findViewById(R.id.breezoaqi);
        mSearchCity = (EditText) findViewById(R.id.citySearch);
        listView = (ListView) findViewById(R.id.list);

        mSearchCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) && mSearchCity.getText().length() > 0) {
                    if (Geocoder.isPresent()) {
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                            String location = mSearchCity.getText().toString();
                            Geocoder gc = new Geocoder(getApplicationContext());
                            List<Address> addresses = gc.getFromLocationName(location, 1); // get the found Address Objects

                            Address address = addresses.get(0);
                            double lat = address.getLatitude();
                            double lon = address.getLongitude();
                            searchCity(lat, lon);
                        } catch (IOException e) {
                            // handle the exception
                            Log.e("__error", e.getMessage());
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void searchCity(double lat, double lon) {
        BreezoMeter breezometer = new BreezoMeter();
        breezometer.setOnResultListener(this);
        breezometer.execute(lat, lon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessfulReturn(BreezoMeterEntity entity) {
        setDataToFragment(entity);
    }

    private void setDataToFragment(BreezoMeterEntity entity) {
        ArrayList<String> arrayList = new ArrayList<>();

        // Compteur animé
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, entity.getBreezometerAqi());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                mBreezoAqi.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });

        arrayList.add(entity.getCountry());
        arrayList.add(entity.getHealth());
        arrayList.add(entity.getSport());
        arrayList.add(entity.getInside());
        Animation pushUp = AnimationUtils.loadAnimation(this, R.anim.push_up);

        animator.setDuration(1000);
        listView.setAdapter(new ListAdapter(getApplicationContext(), arrayList, entity.getColor()));
        listView.startAnimation(pushUp);
        animator.start();

        ValueAnimator textAnimationIn = ValueAnimator.ofFloat(18, 42);
        ValueAnimator textAnimationOut = ValueAnimator.ofFloat(42, 24);
        textAnimationIn.setDuration(1000);

        textAnimationIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mBreezoAqi.setTextSize(animatedValue);
            }
        });

        textAnimationOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mBreezoAqi.setTextSize(animatedValue);
            }
        });

        pushUp.start();
        textAnimationIn.start();
//        textAnimationOut.start();
    }
}
