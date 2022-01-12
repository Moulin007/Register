package com.example.registor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.registor.R;
import com.example.registor.model.SliderModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {

    Context context;
    ArrayList<SliderModel> arrayList;
    ViewPager2 viewPager2;

    public SliderAdapter(Context context, ArrayList<SliderModel> arrayList, ViewPager2 viewPager2) {
        this.context = context;
        this.arrayList = arrayList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.slider_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SliderAdapter.ViewHolder holder, int position) {



        holder.imageView.setImageResource(arrayList.get(position).getImages());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.sliderImageView);

        }
    }
}
