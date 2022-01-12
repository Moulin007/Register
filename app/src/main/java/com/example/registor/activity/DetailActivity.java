package com.example.registor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.registor.R;
import com.example.registor.adapter.DetailAdapter;
import com.example.registor.model.DetailModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {

    ArrayList<DetailModel> arrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.e(TAG, "onCreate: ");

        recyclerView = findViewById(R.id.detailRecyclerVeiw);

        getData();
    }

    public void getData(){

        arrayList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String groupName = dataSnapshot.getKey();

                    DetailModel model = new DetailModel();

                    model.setGroupName(groupName);

                    arrayList.add(model);

                }

                GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailActivity.this, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                DetailAdapter adapter = new DetailAdapter(arrayList, DetailActivity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(DetailActivity.this, MainScreen.class));
        finish();
    }
}