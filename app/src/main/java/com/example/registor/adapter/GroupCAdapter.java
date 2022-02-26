package com.example.registor.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registor.R;
import com.example.registor.model.AttendanceModel;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class GroupCAdapter extends BaseAdapter {

    Context context;
    ArrayList<AttendanceModel> arrayList;

    int flag = 0;

    TextView groupc;

    public GroupCAdapter(Context context, ArrayList<AttendanceModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.groupc_c, parent, false);

        groupc = convertView.findViewById(R.id.GroupC);

        groupc.setText(arrayList.get(position).getGroup3());

        return convertView;
    }
}
