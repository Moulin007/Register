package com.example.registor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.example.registor.R;
import com.example.registor.adapter.FoldingContactListAdapter;
import com.example.registor.model.DetailItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class FoldingContactList extends AppCompatActivity {

    FoldingContactListAdapter adapter;
    ArrayList<DetailItem> arrayList;
    ListView listView;

    String getGroupName;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folding_contact_list);

        listView = findViewById(R.id.detailListView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");

        arrayList = new ArrayList<>();
        new getData().execute();

        listView.setOnItemClickListener((parent, view, position, id) -> {

            ((FoldingCell) view).toggle(false);
            adapter.registerToggle(position);

        });
    }

    public class getData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            getGroupName = getIntent().getStringExtra("groupName");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

            reference.child(getGroupName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    arrayList.clear();

                    String groupName, UserId, groupNo, name, fatherName, education, phonenumber, city, address, whatsappNo, birthdate, age, cast, referancePersonName, referancePersonNo, area, school, profileImage;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        if (dataSnapshot.child("profileImage").getValue() != null) {

                            UserId = dataSnapshot.getKey();

                            name = (String) dataSnapshot.child("name").getValue();
                            fatherName = (String) dataSnapshot.child("fatherName").getValue();
                            education = dataSnapshot.child("education").getValue().toString();
                            phonenumber = (String) dataSnapshot.child("phoneNumber").getValue();
                            city = (String) dataSnapshot.child("city").getValue();
                            address = (String) dataSnapshot.child("address").getValue();
                            whatsappNo = (String) dataSnapshot.child("whatsappNumber").getValue();
                            birthdate = (String) dataSnapshot.child("birthDate").getValue();
                            age = (String) dataSnapshot.child("age").getValue();
                            groupNo = (String) dataSnapshot.child("groupNo").getValue();
                            cast = dataSnapshot.child("cast").getValue().toString();
                            referancePersonName = (String) dataSnapshot.child("referancePersonName").getValue();
                            referancePersonNo = (String) dataSnapshot.child("referancePersonNumber").getValue();
                            area = (String) dataSnapshot.child("area").getValue();
                            school = dataSnapshot.child("school").getValue().toString();
                            profileImage = (String) dataSnapshot.child("profileImage").getValue();


                            DetailItem item = new DetailItem();

                            item.setName(name);
                            item.setFatherName(fatherName);
                            item.setEducation(education);
                            item.setPhno(phonenumber);
                            item.setCity(city);
                            item.setAddress(address);
                            item.setWhatsappNo(whatsappNo);
                            item.setBirthDate(birthdate);
                            item.setAge(age);
                            item.setGroupNo(groupNo);
                            item.setGroupName(getGroupName);
                            item.setCast(cast);
                            item.setReferancePersonName(referancePersonName);
                            item.setReferancePersonNo(referancePersonNo);
                            item.setArea(area);
                            item.setSchool(school);
                            item.setProfileImage(profileImage);
                            item.setUserId(UserId);

                            arrayList.add(item);

                        } else if (dataSnapshot.child("profileImage").getValue() == null) {

                            name = (String) dataSnapshot.child("name").getValue();
                            fatherName = (String) dataSnapshot.child("fatherName").getValue();
                            education = (String) dataSnapshot.child("education").getValue();
                            phonenumber = (String) dataSnapshot.child("phoneNumber").getValue();
                            city = (String) dataSnapshot.child("city").getValue();
                            address = (String) dataSnapshot.child("address").getValue();
                            whatsappNo = (String) dataSnapshot.child("whatsappNumber").getValue();
                            birthdate = (String) dataSnapshot.child("birthDate").getValue();
                            age = (String) dataSnapshot.child("age").getValue();
                            groupNo = (String) dataSnapshot.child("groupNo").getValue();
                            cast = (String) dataSnapshot.child("cast").getValue();
                            referancePersonName = (String) dataSnapshot.child("referancePersonName").getValue();
                            referancePersonNo = (String) dataSnapshot.child("referancePersonNumber").getValue();
                            area = (String) dataSnapshot.child("area").getValue();
                            school = (String) dataSnapshot.child("school").getValue();
                            UserId = dataSnapshot.getKey();


                            DetailItem item = new DetailItem();

                            item.setName(name);
                            item.setFatherName(fatherName);
                            item.setEducation(education);
                            item.setPhno(phonenumber);
                            item.setCity(city);
                            item.setAddress(address);
                            item.setWhatsappNo(whatsappNo);
                            item.setBirthDate(birthdate);
                            item.setAge(age);
                            item.setGroupNo(groupNo);
                            item.setGroupName(getGroupName);
                            item.setCast(cast);
                            item.setReferancePersonName(referancePersonName);
                            item.setReferancePersonNo(referancePersonNo);
                            item.setArea(area);
                            item.setSchool(school);
                            item.setUserId(UserId);

                            arrayList.add(item);

                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            progressDialog.dismiss();

            adapter = new FoldingContactListAdapter(FoldingContactList.this, arrayList);
            listView.setAdapter(adapter);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(FoldingContactList.this, DetailActivity.class));
        finish();
    }
}