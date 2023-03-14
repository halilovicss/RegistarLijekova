package com.halilovic.registarlijekova.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.halilovic.registarlijekova.Adapter.MedicamentsAdapter;
import com.halilovic.registarlijekova.Api.Client;
import com.halilovic.registarlijekova.Base.Database;
import com.halilovic.registarlijekova.Model.CategoryModel;
import com.halilovic.registarlijekova.Model.LijekoviModel;
import com.halilovic.registarlijekova.Model.Model;
import com.halilovic.registarlijekova.databinding.FragmentRegistarLijekovaBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistarLijekova extends Fragment{
private FragmentRegistarLijekovaBinding viewbinding;
private MedicamentsAdapter adapter;
private ArrayList<Model> list;
private Database database;
private List<String> categoryList;
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
        getCategoryList();

        //pretrazivanje po nazivu lijeka
        viewbinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                if (newText.length() > 1){
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        // ukoliko korisnik klikne na x u search widgetu
        viewbinding.search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        // klikom na kategoriju filtriraj podatke
        viewbinding.spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // ako je izabrano prazno polje prikazi sve podatke
                if (parent.getItemAtPosition(position).toString().equals("")){
                    filter("");
                    adapter.notifyDataSetChanged();
                }
                filterCategory(parent.getItemAtPosition(position).toString().trim().toLowerCase());
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewbinding = null;
    }

// ubaci podatke u adapter i adapter proslijedi u recyclearview
    private void getDataToRV(){
    list = database.readData();
    adapter = new MedicamentsAdapter(getContext(),list);
    viewbinding.rvItemMedicine.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    }

// klikom na lijek otvori novi fragment i proslijedi podatke



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
    // pretraga lijekova po nazivu
    private void filter(String text) {

        ArrayList<Model> filteredlist = new ArrayList<>();
        for (Model item : list) {
            if (item.getName().toLowerCase().contains(text)) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
        } else {
            adapter.filterList(filteredlist);
        }
    }
    // dobij kategorije u spinner iz baze podataka, prikazi samo kategorije u kojima se nalaze lijekovi

    private void getCategoryList(){
        categoryList = new ArrayList<>();
        List<String> newList = new ArrayList<>();
        newList = database.readCategory();
        categoryList.add("");
        categoryList.addAll(newList);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        viewbinding.spinnerCat.setAdapter(adapter);
    }

    // metoda za pretrazivanje lijekova prema kategoriji
    private void filterCategory(String text) {

        ArrayList<Model> filteredlist = new ArrayList<>();

        for (Model item : list) {
            if (item.getName_cat().toLowerCase().equals(text)) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {

        } else {
            adapter.filterList(filteredlist);
        }
    }

}