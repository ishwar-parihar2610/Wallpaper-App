package com.example.wallpaperapp.adapter;

import android.database.DatabaseUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowAnimationFrameStats;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpaperapp.R;
import com.example.wallpaperapp.databinding.ImageItemLayoutBinding;
import com.example.wallpaperapp.model.ImageModel;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.viewHolder> {
    private ArrayList<ImageModel> list;
    private LayoutInflater inflater;

    public ImageAdapter(ArrayList<ImageModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ImageAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater==null){
            inflater=LayoutInflater.from(parent.getContext());
        }
        return new viewHolder(DataBindingUtil.inflate(inflater,R.layout.image_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.viewHolder holder, int position) {
        Glide.with(inflater.getContext()).load(list.get(position).getUrls().getRegular())
                .into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageItemLayoutBinding binding;
        public viewHolder(ImageItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
