package com.example.passwordmanagerfixed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    Button btnNext, btnGetStarted;
    int position = 0;
    Animation btnAnim;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make the activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // quand cette Activité se lance, on check si l'intro a deja ete faite

        if (restorePrefData()){

            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();

        }


        setContentView(R.layout.activity_intro);

        // Hide the action Bar

        //getSupportActionBar().hide();

        // ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);


        // fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Fresh Food", "Hello Je fais un test et comme je n'ai aucune imagination je vais mettre beaucoup de texte trés nul", R.drawable.img1));
        mList.add(new ScreenItem("Fast Delivery", "Si un de vous a des idées d'images que je peux mettre ou creer sur ses 3 slides (ou plus) je vous serais d'une eternelle reconnaissance :)", R.drawable.img2));
        mList.add(new ScreenItem("Easy Payment", "blablablablablablablablablablablablablablablablablablabl(t'as vu y'a un bouton skip)\nBTW: C'est la derniere fois que tu me verra alors profites :(", R.drawable.img3));

        // Setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // Setup Table Layout with viewpager


        // next button click Listener

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size() - 1){ //find de la presentation
                    // TODO: show the GETSTARTED button and hide the indicators and the next button

                    loadLastScreen();

                }

            }
        });

        // Table Layout add change listener

        //tabIndicator.addOnTabSelectedListener(new )


        // getstarted button click
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open main activity
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                // we need to save a bool value to storage so next time we open the app the user doesn't have the intro page again

                savePrefsData();
                finish();


            }
        });

        // skip button

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
                loadLastScreen();
            }
        });

        screenPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position2, float positionOffset, int positionOffsetPixels) {
                position++;
            }

            @Override
            public void onPageSelected(int position2) {
                if (position2 == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntro = pref.getBoolean("doneIntro", false);
        return isIntro;

    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPreds", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("doneIntro", true);
        editor.commit();


    }

    // show the GETSTARTED button and hide the indicators and the next button
    private void loadLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        //tabIndicator.setVisibility(View.INVISIBLE);

        // TODO: add an animation to the getstarted button

        // Setup l'animation

        btnGetStarted.setAnimation(btnAnim);

    }
}

