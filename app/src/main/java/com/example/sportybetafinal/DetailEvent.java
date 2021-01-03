package com.example.sportybetafinal;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sportybetafinal.Entities.Evenement;
import com.example.sportybetafinal.Entities.Participants;
import com.example.sportybetafinal.Retrofit.INodeJS;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEvent extends Fragment {
    SharedPreferences sharedPreferencesV, sharedPreferencesU, sharedPreferencesUE;

    Button gotop, participer;
    List<Participants> partList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ImageView imdu, img;
    TextView titre;
    TextView location_event;
    TextView username;
    TextView price;
    TextView description;
    TextView date;
    TextView info;
    TextView type;
    TextView nbplace;
    INodeJS myAPI;
    String x;


    public DetailEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_event, container, false);

        sharedPreferencesV = getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
        sharedPreferencesU = getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        sharedPreferencesUE = getContext().getSharedPreferences("UserEvent", Context.MODE_PRIVATE);
        String nom_evanement = sharedPreferencesV.getString("evenement_nom", "");
        String location_evenement = sharedPreferencesV.getString("evenement_location", "");
        String nom_user = sharedPreferencesUE.getString("nomUser", "");
        String prenom_user = sharedPreferencesUE.getString("prenomUser", "");
        String description_evenement = sharedPreferencesV.getString("evenement_desc", "");
        String dateD = sharedPreferencesV.getString("evenement_date", "");
        String type_evenement = sharedPreferencesV.getString("evenement_type", "");
        int prixevent = sharedPreferencesV.getInt("evenement_price", 0);
        final int nbplaces = sharedPreferencesV.getInt("evenement_nbplace", 0);
        int infoline_evenement = sharedPreferencesV.getInt("evenement_infoline", 0);
        final int id_cu = sharedPreferencesU.getInt("idUser", 0);
        final int id_ev = sharedPreferencesV.getInt("id_evenement", 0);

        gotop = rootView.findViewById(R.id.gotoprofil);
        imdu = rootView.findViewById(R.id.imguser);
        titre = rootView.findViewById(R.id.title);
        location_event = rootView.findViewById(R.id.category);
        username = rootView.findViewById(R.id.username);
        price = rootView.findViewById(R.id.price);
        img = rootView.findViewById(R.id.img);
        description = rootView.findViewById(R.id.description);
        date = rootView.findViewById(R.id.date);
        type = rootView.findViewById(R.id.category);
        nbplace = rootView.findViewById(R.id.nbplaceform);
        info = rootView.findViewById(R.id.info);
        participer = rootView.findViewById(R.id.participer);
        participer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                Retrofit retrofit = builder.build();
                myAPI = retrofit.create(INodeJS.class);
                compositeDisposable.add(myAPI.addParticipant(id_cu, id_ev)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s)
                                    throws Exception {
                                participer.setText("deja participer!");
                                System.out.println("evenement ajouté");
                            }
                        })
                );
                //updateProfile(id_ev,nbplaces);
            }
        });
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myAPI = retrofit.create(INodeJS.class);
        Call<List<Participants>> call = myAPI.getParticipantList();
        call.enqueue(new Callback<List<Participants>>() {
            @Override
            public void onResponse(Call<List<Participants>> call, Response<List<Participants>> response) {
                partList = response.body();
                Log.d("test2", String.valueOf(response.body()));
                int i;
                try {
                    for (i = 0; i < partList.size(); i++) {
                        if (partList.get(i).getId_evenement() == id_ev && partList.get(i).getId_user() == id_cu) {
                            participer.setText("deja participer!");
                        }
                    }
                } catch (NullPointerException Ignored) {
                }
            }

            @Override
            public void onFailure(Call<List<Participants>> call, Throwable t) {

            }
        });

        location_event.setText(location_evenement);
        type.setText(type_evenement);

        if (type_evenement.contentEquals("Football")) {
            img.setImageResource(R.drawable.foot);
        } else if (type_evenement.contentEquals("Tennis")) {
            img.setImageResource(R.drawable.tennis);
        } else if (type_evenement.contentEquals("Basketball")) {
            img.setImageResource(R.drawable.basket);
        } else if (type_evenement.contentEquals("VolleyBall")) {
            img.setImageResource(R.drawable.volley);
        } else if (type_evenement.contentEquals("handball")) {
            img.setImageResource(R.drawable.volley);
        }

        System.out.println(type_evenement + "hhhhhhhhhhhhhhhhhhh");
        //private static final String[] cats = {"Football", "BasketBall","VolleyBall","HandBall"};


        titre.setText(nom_evanement);
        //price.setText(prixevent);
        description.setText(description_evenement);
        date.setText("à venir le :" + dateD);
        info.setText(String.valueOf(infoline_evenement));

        nbplace.setText(String.valueOf(nbplaces));
        System.out.println(nom_user + " " + prenom_user + "zzzzzzzzzzzzzzzzzz");


        //get image
/*        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();*/
        myAPI = retrofit.create(INodeJS.class);


        return rootView;
    }
    /*private void updateProfile(int id_ev, int nbplace_evenement){
        //sharedPreferences = getContext().getSharedPreferences("testt", Context.MODE_PRIVATE);
        //int userId = sharedPreferences.getInt("idUser",0);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myAPI = retrofit.create(INodeJS.class);
        Call<Evenement> call = myAPI.updateNbrPlace(id_ev, nbplace_evenement);
        call.enqueue(new Callback<Evenement>() {
            @Override
            public void onResponse(Call<Evenement> call, Response<Evenement> response) {

                Toast.makeText(getContext(),response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Evenement> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }*/


}
