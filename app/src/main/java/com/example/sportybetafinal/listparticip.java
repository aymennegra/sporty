package com.example.sportybetafinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sportybetafinal.Adapters.EvenementUserAdapter;
import com.example.sportybetafinal.Entities.Evenement;
import com.example.sportybetafinal.Adapters.EvenementAdapter;
import com.example.sportybetafinal.Entities.Participants;
import com.example.sportybetafinal.Entities.User;
import com.example.sportybetafinal.R;
import com.example.sportybetafinal.Retrofit.INodeJS;
import com.example.sportybetafinal.Retrofit.RetrofitClient;
import com.example.sportybetafinal.Utils.database.AppDataBase;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class listparticip extends Fragment {

    ImageView acceuil1,profileuser,favori;
    List<Evenement> paticipList ;

    RecyclerView recyclerView;
    SharedPreferences sharedPreferencesU;

    INodeJS myAPI;
    Context mContext;
    private AppDataBase database;
    public static INodeJS iNodeJS;


    public listparticip() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {






        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_evenements, container, false);


        recyclerView = rootView.findViewById(R.id.publications);
        favori = rootView.findViewById(R.id.favori);
        iNodeJS = RetrofitClient.getInstance().create(INodeJS.class);

        GetListparticip();



        return rootView;
    }



    public void GetListparticip() {
        sharedPreferencesU = getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        int idu = sharedPreferencesU.getInt("idUser", 0);
        System.out.println(idu);

        Call<List<Evenement>> call = iNodeJS.getparticipList(idu);
        call.enqueue(new Callback<List<Evenement>>() {
            @Override
            public void onResponse(Call<List<Evenement>> call, Response<List<Evenement>> response) {
                paticipList = response.body();
                Log.d("test2", String.valueOf(response.body()));
                EvenementUserAdapter adapter = new EvenementUserAdapter(mContext, paticipList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            }

            @Override
            public void onFailure(Call<List<Evenement>> call, Throwable t) {

            }
        });
    }












}

