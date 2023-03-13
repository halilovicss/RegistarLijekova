package com.halilovic.registarlijekova.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.halilovic.registarlijekova.Model.Model;
import com.halilovic.registarlijekova.databinding.ItemMedicamentsBinding;

import java.util.ArrayList;

public class MedicamentsAdapter extends RecyclerView.Adapter<MedicamentsAdapter.ViewHolder>   {
    private Context context;
    private ArrayList<Model> list;
    private OnItemClickListener onItemClick;

    public MedicamentsAdapter(Context context, ArrayList<Model> list, OnItemClickListener onItemClick) {
        this.context = context;
        this.list = list;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public MedicamentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicamentsAdapter.ViewHolder(ItemMedicamentsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentsAdapter.ViewHolder holder, int position) {
        Model model = list.get(position);
        holder.binding.txtMedicineTitle.setText(model.getName());
        holder.binding.txtMedicineCategory.setText(model.getAtc() + " - " +model.getName_cat());
        holder.binding.colorMedicine.setBackgroundColor(Color.parseColor(model.getColor()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.onItemClick = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder   {
        public ItemMedicamentsBinding binding;
        public ViewHolder(@NonNull ItemMedicamentsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }


    }
}
