package com.example.cryptocurrencyapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.cryptocurrencyapp.R;
import com.example.cryptocurrencyapp.adaptor.CustomAdaptor;
import com.example.cryptocurrencyapp.adaptor.CustomItemClickListener;
import com.example.cryptocurrencyapp.model.CryptoModel;
import com.example.cryptocurrencyapp.service.ApiClient;
import com.example.cryptocurrencyapp.service.ICryptoAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CryptoModel> cryptoModels;
    private RecyclerView currencyList;
    private CustomAdaptor customAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyList = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        currencyList.setLayoutManager(mLayoutManager);
        currencyList.setItemAnimator(new DefaultItemAnimator());

        getData();

    }

    public void getData(){

        progressDialog = createProgressDialog(MainActivity.this);

        ICryptoAPI cryptoAPI = ApiClient.getClient().create(ICryptoAPI.class);

        Call<List<CryptoModel>> call = cryptoAPI.getCurrencies();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    List<CryptoModel> responseList = response.body();
                    if (responseList != null){
                        cryptoModels = new ArrayList<CryptoModel>(responseList);
                        //System.out.println(cryptoModels.get(0).getPrice());
                        customAdapter = new CustomAdaptor(MainActivity.this, cryptoModels, new CustomItemClickListener() {
                            @Override
                            public void onItemClick(CryptoModel crypto, int position) {

                                Toast.makeText(getApplicationContext(),""+crypto.getName(),Toast.LENGTH_SHORT).show();

                            }
                        });
                        currencyList.setAdapter(customAdapter);
                    }



                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_layout);
        return dialog;
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}