package com.example.registor.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    ListView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6,
    recyclerView7, recyclerView8, recyclerView9, recyclerView10;
    ArrayList<AttendanceModel> arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6,
    arrayList7, arrayList8, arrayList9, arrayList10;

    AutoCompleteTextView date;

    String one, two, three, four, five, six, seven, eight, nine, ten, key, name, mobileNumber;

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
        recyclerView7 = findViewById(R.id.recyclerView7);
        recyclerView8 = findViewById(R.id.recyclerView8);
        recyclerView9 = findViewById(R.id.recyclerView9);
        recyclerView10 = findViewById(R.id.recyclerView10);

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
//            recyclerView5.setAdapter(adapter5);
//            recyclerView6.setAdapter(adapter6);

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
        arrayList7 = new ArrayList<>();
        arrayList8 = new ArrayList<>();
        arrayList9 = new ArrayList<>();
        arrayList10 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String key = dataSnapshot.getKey();

                    if (key.contains("1")) {

                        reference.child("1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                    AttendanceModel model = new AttendanceModel();

                                    one = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup1(one);
                                    model.setId("1");
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
                    if (key.contains("2")) {

                        reference.child("2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList2.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                    AttendanceModel model = new AttendanceModel();

                                    two = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup2(two);
                                    model.setId("2");
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
                    if (key.contains("3")) {

                        reference.child("3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList3.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                    AttendanceModel model = new AttendanceModel();

                                    three = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot.child("phoneNumber").getValue();

                                    model.setGroup3(three);
                                    model.setId("3");
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
                    if (key.contains("4")){

                        reference.child("4").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList4.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    four = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup4(four);
                                    model.setId("4");
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
                    if (key.contains("5")){

                        reference.child("5").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList5.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    five = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup5(five);
                                    model.setId("5");
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
                    if (key.contains("6")){

                        reference.child("6").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList6.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    six = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup6(six);
                                    model.setId("6");
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
                    if (key.contains("7")){

                        reference.child("7").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList7.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    seven = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup7(seven);
                                    model.setId("7");
                                    model.setName(name);
                                    model.setMobilenumber(mobileNumber);

                                    arrayList7.add(model);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    if (key.contains("8")){

                        reference.child("8").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList8.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    eight = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup8(eight);
                                    model.setId("8");
                                    model.setName(name);
                                    model.setMobilenumber(mobileNumber);

                                    arrayList8.add(model);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    if (key.contains("9")){

                        reference.child("9").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList9.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    nine = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup9(nine);
                                    model.setId("9");
                                    model.setName(name);
                                    model.setMobilenumber(mobileNumber);

                                    arrayList9.add(model);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    if (key.contains("10")){

                        reference.child("10").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList10.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                    AttendanceModel model = new AttendanceModel();

                                    ten = (String) dataSnapshot1.child("groupNo").getValue();
                                    name = (String) dataSnapshot1.child("name").getValue();
                                    mobileNumber = (String) dataSnapshot1.child("phoneNumber").getValue();

                                    model.setGroup10(ten);
                                    model.setId("10");
                                    model.setName(name);
                                    model.setMobilenumber(mobileNumber);

                                    arrayList10.add(model);

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