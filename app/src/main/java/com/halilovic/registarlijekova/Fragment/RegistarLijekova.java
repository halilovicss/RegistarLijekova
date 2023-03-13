package com.halilovic.registarlijekova.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.halilovic.registarlijekova.Adapter.MedicamentsAdapter;
import com.halilovic.registarlijekova.Adapter.OnItemClickListener;
import com.halilovic.registarlijekova.Api.Client;
import com.halilovic.registarlijekova.Base.Database;
import com.halilovic.registarlijekova.Model.CategoryModel;
import com.halilovic.registarlijekova.Model.LijekoviModel;
import com.halilovic.registarlijekova.Model.Model;
import com.halilovic.registarlijekova.R;
import com.halilovic.registarlijekova.databinding.FragmentRegistarLijekovaBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistarLijekova extends Fragment implements OnItemClickListener {
private FragmentRegistarLijekovaBinding viewbinding;
private MedicamentsAdapter adapter;
private ArrayList<Model> list;
private Database database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      viewbinding = FragmentRegistarLijekovaBinding.inflate(getLayoutInflater());
      return  viewbinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        viewbinding.rvItemMedicine.setLayoutManager(new LinearLayoutManager(getActivity()));
        database = new Database(getContext());
        //provjeri da li u bazi postoje lijekovi
        if (database.isEmpty("lijekovi")){
            getMedicals();
            saveCategories();
        }else  {


        }
        getDataToRV();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewbinding = null;
    }


    private void getDataToRV(){
    list = database.readData();
    adapter = new MedicamentsAdapter(getContext(),list,this::onItemClick);
    viewbinding.rvItemMedicine.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    }

// klikom na lijek otvori novi fragment i proslijedi podatke
    @Override
    public void onItemClick(View view, int item) {

        Bundle bundle = new Bundle();
        bundle.putInt("id",list.get(item).getId_lijeka());
        bundle.putString("name",list.get(item).getName());
        bundle.putString("atc",list.get(item).getAtc());
        bundle.putString("category",list.get(item).getName_cat());
        bundle.putString("shortDescription",list.get(item).getShortDescription());
        bundle.putString("description",list.get(item).getDescription());
        bundle.putInt("activeSubstanceValue",list.get(item).getActiveSubstanceValue());
        bundle.putInt("omjer",list.get(item).getActiveSubstanceSelectedQuantity());
        bundle.putInt("min",list.get(item).getMinimumDailyDose());
        bundle.putInt("max",list.get(item).getMaximumDailyDose());


        Navigation.findNavController(view).navigate(R.id.action_registarLijekova_to_medicamentDetails,bundle);

    }


    //ucitaj vrijednosti iz baze i proslijedi u listu
    private void getMedicals(){
        Call<ArrayList<LijekoviModel>> call = Client.getInstance().getApi().getMedicaments();
        call.enqueue(new Callback<ArrayList<LijekoviModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LijekoviModel>> call, Response<ArrayList<LijekoviModel>> response) {
                if (response.isSuccessful()){
                    Log.d("TAG", "onResponse: " + response.body().get(0).getDescription());
                    database.addMedicaments(response.body());
                  getDataToRV();

                }else{
                    Log.d("TAG", "onResponse: Greska ucitavanja");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LijekoviModel>> call, Throwable t) {
                Log.d("TAG", "onResponse: Greska ucitavanja: " + t.getMessage());

            }
        });
    }


    //metoda za ucitavanje kategorija sa baze i ucitavanje u lokalnu bazu
    private void saveCategories(){
        Call<ArrayList<CategoryModel>> callCategory = Client.getInstance().getApi().getCategories();
        callCategory.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                database.addCategory(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {

            }
        });
    }
}