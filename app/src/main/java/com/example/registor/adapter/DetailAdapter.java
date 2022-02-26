package com.example.registor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registor.activity.FoldingContactList;
import com.example.registor.R;
import com.example.registor.model.DetailModel;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    ArrayList<DetailModel> arrayList;
    Context context;

    public DetailAdapter(ArrayList<DetailModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.detail_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.groupName.setText(arrayList.get(position).getGroupName());

        holder.itemView.setOnClickListener(v -> {

            context.startActivity(new Intent(context, FoldingContactList.class)
                    .putExtra("groupName", arrayList.get(position).getGroupName()));

        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            groupName = itemView.findViewById(R.id.contactSurname);
        }
    }
}
