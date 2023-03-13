package com.halilovic.registarlijekova.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halilovic.registarlijekova.Api.Client;
import com.halilovic.registarlijekova.Model.SubstanceModel;
import com.halilovic.registarlijekova.R;
import com.halilovic.registarlijekova.databinding.FragmentMedicamentDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicamentDetails extends Fragment {
private FragmentMedicamentDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     binding = FragmentMedicamentDetailsBinding.inflate(getLayoutInflater());
     return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ucitaj proslijedjene vrijednosti na UI
        assert getArguments() != null;
        int id = getArguments().getInt("id");
        String name = getArguments().getString("name");
        String atc = getArguments().getString("atc");
        String category = getArguments().getString("category");
        String shortDesc = getArguments().getString("shortDescription");
        String description = getArguments().getString("description");
        int activeSubstanceValue = getArguments().getInt("activeSubstanceValue");
        int omjer = getArguments().getInt("omjer");
        int min = getArguments().getInt("min");
        int max = getArguments().getInt("max");


        binding.txtName.setText(name);
        binding.txtCat.setText(atc + " - " + category);
        binding.txtShortDesc.setText(shortDesc);
        binding.txtOpisLijeka.setText(description);

        if (activeSubstanceValue == 0){
            binding.txtActiveSubstance.setText("/");

        }else{
            binding.txtActiveSubstance.setText(activeSubstanceValue +"");

        }

        if (omjer == 0){
            binding.txtOmjerSupstance.setText("/");

        }else{
            binding.txtOmjerSupstance.setText(omjer +"");

        }
        if (min == 0 && max == 0){
            binding.txtPreporuceneDoze.setText("/");

        }else{
            binding.txtPreporuceneDoze.setText("Min "+ min + " - Max:" + max);

        }

        getActiveSubstance(id);
        loadImage();

        //ukoliko je tekst duzi na textview aktiviraj scrollbars
        binding.txtOpisLijeka.setMovementMethod(new ScrollingMovementMethod());
        //klikom na dugme back vrati nazad
      binding.txtBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

    }
    // metoda za ucitavanje internet slike, koristena Picasso biblioketa
    private void loadImage() {
        String IMAGE_URL = "https://cdn-icons-png.flaticon.com/512/883/883356.png";
        Picasso.get()
                .load(IMAGE_URL)
                .into(binding.imgDetails);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //metoda za ucitavanje aktivne supstance proslijedjivanjem id broja lijeka
        private void getActiveSubstance(int id){
            Call<ArrayList<SubstanceModel>> call = Client.getInstance().getApi().getSubstance(id);
            call.enqueue(new Callback<ArrayList<SubstanceModel>>() {
                @Override
                public void onResponse(Call<ArrayList<SubstanceModel>> call, Response<ArrayList<SubstanceModel>> response) {
                    if (response.isSuccessful())
                        binding.txtNazivAkSupstance.setText(getString(R.string.aktivna_supstanca) + " " + response.body().get(0).getName());
                }

                @Override
                public void onFailure(Call<ArrayList<SubstanceModel>> call, Throwable t) {

                }
            });

        }
}
