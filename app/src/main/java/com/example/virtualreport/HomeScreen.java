package com.example.virtualreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.LayoutTransition;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.TimeUnit;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView NavView;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private BlurView BlurView;

    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        toolbar = findViewById(R.id.Toolbar);
        NavView = findViewById(R.id.NavView);
        BlurView = findViewById(R.id.blur_layout);

        isOpen = false;
        BlurView.setVisibility(View.INVISIBLE);

        Fragment fragment;
        fragment = new ReportFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMenu();
            }
        });

        setNavigationViewListener();
        BlurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMenu();
            }
        });

    }

    private void BlurBackground(float Radius,float in, float out) {

        float radius = Radius;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        BlurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);

        AlphaAnimation anim=new AlphaAnimation(in,out);
        anim.setDuration(500);
        BlurView.startAnimation(anim);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment;
        NavView = findViewById(R.id.NavView);

        switch (item.getItemId()) {
            case R.id.report_btn:
                fragment = new ReportFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
                if (isOpen) {
                    float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
                    ViewGroup.LayoutParams layoutParams = NavView.getLayoutParams();
                    layoutParams.width = (int) (width);
                    NavView.setLayoutParams(layoutParams);
                    BlurView.setVisibility(View.INVISIBLE);
                    isOpen = false;
                }
                return true;
            case R.id.rescuers_btn:
                fragment = new RescuerFragment();
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_place, fragment);
                fragmentTransaction2.commit();
                if (isOpen) {
                    float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
                    ViewGroup.LayoutParams layoutParams = NavView.getLayoutParams();
                    layoutParams.width = (int) (width);
                    NavView.setLayoutParams(layoutParams);
                    BlurView.setVisibility(View.INVISIBLE);
                    isOpen = false;
                }
                return true;
            case R.id.tasks_btn:
                fragment = new TasksFragment();
                FragmentManager fragmentManager3 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_place, fragment);
                fragmentTransaction3.commit();
                if (isOpen) {
                    float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
                    ViewGroup.LayoutParams layoutParams = NavView.getLayoutParams();
                    layoutParams.width = (int) (width);
                    NavView.setLayoutParams(layoutParams);
                    BlurView.setVisibility(View.INVISIBLE);
                    isOpen = false;
                }
                return true;
        }
        return true;
    }

    private void handleMenu() {
        if (isOpen) {
            AlphaAnimation anim=new AlphaAnimation(1.0f,0.0f);
            anim.setDuration(250);
            BlurView.setVisibility(View.INVISIBLE);
            BlurView.startAnimation(anim);
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
            ViewGroup.LayoutParams layoutParams = NavView.getLayoutParams();
            layoutParams.width = (int) (width);
            NavView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            NavView.setLayoutParams(layoutParams);
            isOpen = false;
        } else {
            BlurView.setVisibility(View.VISIBLE);
            BlurView.getLayoutTransition().enableTransitionType(LayoutTransition.APPEARING);
            BlurBackground(8f,0.0f,1.0f);
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
            ViewGroup.LayoutParams layoutParams = NavView.getLayoutParams();
            layoutParams.width = (int) (width);
            NavView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            NavView.setLayoutParams(layoutParams);
            isOpen = true;
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.NavView);
        navigationView.setNavigationItemSelectedListener(this);
    }


}
