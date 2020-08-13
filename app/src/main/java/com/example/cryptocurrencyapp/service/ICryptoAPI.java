package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICryptoAPI {

    @GET("currencies/ticker?key={add api key from nomics api}")
    Call<List<CryptoModel>> getCurrencies();
}
