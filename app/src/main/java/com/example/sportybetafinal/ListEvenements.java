package com.example.sportybetafinal;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sportybetafinal.Entities.Evenement;
import com.example.sportybetafinal.Adapters.EvenementAdapter;
import com.example.sportybetafinal.R;
import com.example.sportybetafinal.Retrofit.INodeJS;
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
public class ListEvenements extends Fragment {

    ImageView acceuil1,profileuser,favori;
    List<Evenement> evenementsList ;

    RecyclerView recyclerView;

    INodeJS myAPI;
    Context mContext;
    private AppDataBase database;
    private List<Evenement> event_list = new ArrayList<>();


    public ListEvenements() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        GetListEvenement();



        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_evenements, container, false);


        recyclerView = rootView.findViewById(R.id.publications);
        favori = rootView.findViewById(R.id.favori);

        return rootView;
    }



    public void GetListEvenement() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myAPI = retrofit.create(INodeJS.class);
        Call<List<Evenement>> call = myAPI.getEventsList();
        call.enqueue(new Callback<List<Evenement>>() {
            @Override
            public void onResponse(Call<List<Evenement>> call, Response<List<Evenement>> response) {
                evenementsList = response.body();
                Log.d("test2", String.valueOf(response.body()));
                //recyclerView = (R.id.publications);
                EvenementAdapter adapter = new EvenementAdapter(mContext, evenementsList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            }

            @Override
            public void onFailure(Call<List<Evenement>> call, Throwable t) {

            }
        });
    }












}
