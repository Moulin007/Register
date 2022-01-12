package com.example.registor.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

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

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    Context context;
    ArrayList<AttendanceModel> arrayList;
    int flag = 0;

    DatabaseReference databaseReference;

    public AttendanceAdapter(Context context, ArrayList<AttendanceModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.attendance_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.groupA.setText(arrayList.get(position).getGroupA());

        holder.checkBox.setOnClickListener(v -> {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Attendance").child(arrayList.get(position).getId());

            if (holder.checkBox.isChecked()) {


                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", arrayList.get(position).getGroupA());
                user.put("name", arrayList.get(position).getName());
                user.put("phoneNumber", arrayList.get(position).getMobilenumber());

                databaseReference.push().setValue(user);

            }else if (!holder.checkBox.isChecked()){

                Query query = databaseReference.orderByChild("name").equalTo(arrayList.get(position).getName());
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

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupA;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            groupA = itemView.findViewById(R.id.GroupA);
            checkBox = itemView.findViewById(R.id.GroupAcheck);
        }
    }
}
