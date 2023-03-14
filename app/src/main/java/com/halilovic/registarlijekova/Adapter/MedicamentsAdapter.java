package com.halilovic.registarlijekova.Adapter;

import static java.lang.Math.E;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.halilovic.registarlijekova.Model.LijekoviModel;
import com.halilovic.registarlijekova.Model.Model;
import com.halilovic.registarlijekova.R;
import com.halilovic.registarlijekova.databinding.ItemMedicamentsBinding;

import java.util.ArrayList;
import java.util.List;

public class MedicamentsAdapter extends RecyclerView.Adapter<MedicamentsAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<Model> list;


    public MedicamentsAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MedicamentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicamentsAdapter.ViewHolder(ItemMedicamentsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Model model = list.get(position);
        holder.binding.txtMedicineTitle.setText(model.getName());
        holder.binding.txtMedicineCategory.setText(model.getAtc() + " - " +model.getName_cat());
        holder.binding.colorMedicine.setBackgroundColor(Color.parseColor(model.getColor()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("id",list.get(position).getId_lijeka());
                bundle.putString("name",list.get(position).getName());
                bundle.putString("atc",list.get(position).getAtc());
                bundle.putString("category",list.get(position).getName_cat());
                bundle.putString("shortDescription",list.get(position).getShortDescription());
                bundle.putString("description",list.get(position).getDescription());
                bundle.putInt("activeSubstanceValue",list.get(position).getActiveSubstanceValue());
                bundle.putInt("omjer",list.get(position).getActiveSubstanceSelectedQuantity());
                bundle.putInt("min",list.get(position).getMinimumDailyDose());
                bundle.putInt("max",list.get(position).getMaximumDailyDose());


                Navigation.findNavController(v).navigate(R.id.action_registarLijekova_to_medicamentDetails,bundle);

            }
            public void filterCat(String cat){
                if (model.getName_cat().equals(cat)){

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void showCategory(int category,ArrayList<Model> categoryList){

    }

    public void filterList(ArrayList<Model> filterlist) {

        list = filterlist;

        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder   {
        public ItemMedicamentsBinding binding;
        public ViewHolder(@NonNull ItemMedicamentsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }


    }

}
