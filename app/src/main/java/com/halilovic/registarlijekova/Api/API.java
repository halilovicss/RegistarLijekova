package com.halilovic.registarlijekova.Api;

import com.halilovic.registarlijekova.Model.CategoryModel;
import com.halilovic.registarlijekova.Model.LijekoviModel;
import com.halilovic.registarlijekova.Model.SubstanceModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {


    @GET("medicaments/")
    Call<ArrayList<LijekoviModel>> getMedicaments();


    @GET("categories/")
    Call<ArrayList<CategoryModel>> getCategories();

    @GET("substances")
    Call <ArrayList<SubstanceModel>> getSubstance(@Query("drugid") int id_lijeka);
}
