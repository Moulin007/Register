package com.example.registor.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registor.R;
import com.example.registor.model.AttendanceModel;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class GroupCAdapter extends RecyclerView.Adapter<GroupCAdapter.ViewHolder> {

    Context context;
    ArrayList<AttendanceModel> arrayList;

    int flag = 0;

    public GroupCAdapter(Context context, ArrayList<AttendanceModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.groupc_c, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.groupc.setText(arrayList.get(position).getGroupC());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            groupc = itemView.findViewById(R.id.GroupC);

        }
    }
}
