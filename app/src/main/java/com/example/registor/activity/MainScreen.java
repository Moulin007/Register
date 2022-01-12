package com.example.registor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.registor.R;
import com.example.registor.adapter.SliderAdapter;
import com.example.registor.model.SliderModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    TextView registrationForm, attendance;

    CardView registrationView, attendanceView, detailView;

    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        registrationForm = findViewById(R.id.registrationForm);
        attendance = findViewById(R.id.attendance);

        viewPager2 = findViewById(R.id.viewPager);

        registrationView = findViewById(R.id.RegistrationVeiw);
        attendanceView = findViewById(R.id.AttendanceView);
        detailView = findViewById(R.id.DetailView);

        ArrayList<SliderModel> arrayList = new ArrayList<>();
        arrayList.add(new SliderModel(R.drawable.studio_final));
        arrayList.add(new SliderModel(R.drawable.img1));
        arrayList.add(new SliderModel(R.drawable.img2));
        arrayList.add(new SliderModel(R.drawable.img3));

        viewPager2.setAdapter(new SliderAdapter(this, arrayList, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {

            float r = 1 - Math.abs(position);
            page.setScaleY(0.8f + r * 0.15f);

        });

        viewPager2.setPageTransformer(compositePageTransformer);

        registrationView.setOnClickListener(v -> {

            startActivity(new Intent(MainScreen.this, MainActivity.class));
            finish();

        });

        attendanceView.setOnClickListener(v -> {

            startActivity(new Intent(MainScreen.this, Attendance.class));
            finish();

        });

        detailView.setOnClickListener(v -> {

            startActivity(new Intent(MainScreen.this, DetailActivity.class));
            finish();

        });
    }
}