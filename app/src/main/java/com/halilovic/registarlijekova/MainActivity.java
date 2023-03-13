package com.halilovic.registarlijekova;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.halilovic.registarlijekova.Api.Client;
import com.halilovic.registarlijekova.Base.Database;
import com.halilovic.registarlijekova.Model.CategoryModel;
import com.halilovic.registarlijekova.Model.LijekoviModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



}