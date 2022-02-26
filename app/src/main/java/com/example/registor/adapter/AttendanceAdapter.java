package com.example.registor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.registor.R;
import com.example.registor.model.AttendanceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceAdapter extends BaseAdapter {

    Context context;
    ArrayList<AttendanceModel> arrayList;
    int flag = 0;

    TextView groupA;
    CheckBox checkBox;

    DatabaseReference databaseReference;

    public AttendanceAdapter(Context context, ArrayList<AttendanceModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.attendance_item_layout, viewGroup, false);

        groupA = view.findViewById(R.id.GroupA);
        checkBox = view.findViewById(R.id.GroupAcheck);

        groupA.setText(arrayList.get(i).getGroup1());

        checkBox.setOnClickListener(v -> {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Attendance").child(arrayList.get(i).getId());

            if (checkBox.isChecked()) {


                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", arrayList.get(i).getGroup1());
                user.put("name", arrayList.get(i).getName());
                user.put("phoneNumber", arrayList.get(i).getMobilenumber());

                databaseReference.push().setValue(user);

            }else if (!checkBox.isChecked()){

                Query query = databaseReference.orderByChild("name").equalTo(arrayList.get(i).getName());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            String key = dataSnapshot.getKey();
                            databaseReference.child(key).removeValue();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });

        return view;
    }
}
