package com.example.registor.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registor.R;
import com.example.registor.adapter.AttendanceAdapter;
import com.example.registor.adapter.GroupBAdapter;
import com.example.registor.adapter.GroupCAdapter;
import com.example.registor.adapter.GroupDAdapter;
import com.example.registor.adapter.GroupEAdapter;
import com.example.registor.adapter.GroupTAdapter;
import com.example.registor.model.AttendanceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Attendance extends AppCompatActivity {

    RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6;
    ArrayList<AttendanceModel> arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6;

    AutoCompleteTextView date;

    String a, b, c, d, e, t, key, name, mobileNumber;

    int mYEAR, mMONTH, mDATE;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView4 = findViewById(R.id.recyclerView4);
        recyclerView5 = findViewById(R.id.recyclerView5);
        recyclerView6 = findViewById(R.id.recyclerView6);

        date = findViewById(R.id.date);

        new getData().execute();

        date.setOnClickListener(v -> {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

            Calendar calendar = Calendar.getInstance();

            mYEAR = calendar.get(Calendar.YEAR);
            mMONTH = calendar.get(Calendar.MONTH);
            mDATE = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Attendance.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                }
            }, mYEAR, mMONTH, mDATE);

            datePickerDialog.show();

        });

//        getGroupAData();


    }

    public class getData extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            getGroupAData();

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(Attendance.this));
            recyclerView2.setLayoutManager(new LinearLayoutManager(Attendance.this));
            recyclerView3.setLayoutManager(new LinearLayoutManager(Attendance.this));
            recyclerView4.setLayoutManager(new LinearLayoutManager(Attendance.this));
            recyclerView5.setLayoutManager(new LinearLayoutManager(Attendance.this));
            recyclerView6.setLayoutManager(new LinearLayoutManager(Attendance.this));
            AttendanceAdapter adapter = new AttendanceAdapter(Attendance.this, arrayList);
            GroupBAdapter adapter2 = new GroupBAdapter(Attendance.this, arrayList2);
            GroupCAdapter adapter3 = new GroupCAdapter(Attendance.this, arrayList3);
            GroupDAdapter adapter4 = new GroupDAdapter(Attendance.this, arrayList4);
            GroupEAdapter adapter5 = new GroupEAdapter(Attendance.this, arrayList5);
            GroupTAdapter adapter6 = new GroupTAdapter(Attendance.this, arrayList6);
            recyclerView.setAdapter(adapter);
            recyclerView2.setAdapter(adapter2);
            recyclerView3.setAdapter(adapter3);
            recyclerView4.setAdapter(adapter4);
            recyclerView5.setAdapter(adapter5);
            recyclerView6.setAdapter(adapter6);

            progressDialog.dismiss();
        }
    }

    public void getGroupAData() {

        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        arrayList3 = new ArrayList<>();
        arrayList4 = new ArrayList<>();
        arrayList5 = new ArrayList<>();
        arrayList6 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                arrayList2.clear();
                arrayList3.clear();
                arrayList3.clear();
                arrayList5.clear();
                arrayList6.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String key = dataSnapshot.getKey();

                    if (key.contains("A")) {

                        reference.child("A").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                    AttendanceModel model = new AttendanceModel();

                                    a = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroupA(a);
                                    model.setId("A");
                                    model.setMobilenumber(mobileNumber);
                                    model.setName(name);

                                    arrayList.add(model);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    if (key.contains("B")) {

                        reference.child("B").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                    AttendanceModel model = new AttendanceModel();

                                    b = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroupB(b);
                                    model.setId("B");
                                    model.setMobilenumber(mobileNumber);
                                    model.setName(name);

                                    arrayList2.add(model);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if (key.contains("C")) {

                        reference.child("C").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                    AttendanceModel model = new AttendanceModel();

                                    c = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot.child("phoneNumber").getValue();

                                    model.setGroupC(c);
                                    model.setId("C");
                                    model.setMobilenumber(mobileNumber);
                                    model.setName(name);

                                    arrayList3.add(model);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if (key.contains("D")){

                        reference.child("D").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    d = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroupD(d);
                                    model.setId("D");
                                    model.setName(name);
                                    model.setMobilenumber(mobileNumber);

                                    arrayList4.add(model);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    if (key.contains("E")){

                        reference.child("E").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    e = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroupE(e);
                                    model.setId("E");
                                    model.setName(name);
                                    model.setMobilenumber(mobileNumber);

                                    arrayList5.add(model);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    if (key.contains("T")){

                        reference.child("T").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    t = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroupT(t);
                                    model.setId("T");
                                    model.setName(name);
                                    model.setMobilenumber(mobileNumber);

                                    arrayList6.add(model);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Attendance.this, MainScreen.class));
        finish();

    }
}