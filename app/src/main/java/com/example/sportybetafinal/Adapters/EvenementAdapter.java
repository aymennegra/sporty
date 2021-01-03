package com.example.sportybetafinal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportybetafinal.DetailsActivity;
import com.example.sportybetafinal.DetailsActivity1;
import com.example.sportybetafinal.Entities.Evenement;
import com.example.sportybetafinal.Entities.Participants;
import com.example.sportybetafinal.Entities.User;
import com.example.sportybetafinal.MessageActivity;
import com.example.sportybetafinal.R;
import com.example.sportybetafinal.Retrofit.INodeJS;

import com.example.sportybetafinal.Retrofit.RetrofitClient;
import com.example.sportybetafinal.Utils.database.AppDataBase;
import com.example.sportybetafinal.Map.MapActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class EvenementAdapter extends RecyclerView.Adapter<EvenementAdapter.myViewHolder> {

    Context mContext;
    List<Participants> partList;
    private List<Evenement> mData;
    private List<Evenement> event_list = new ArrayList<>();
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences, sharedPreferencesUE, sharedPreferencesU, sharedPreferencesV;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ImageView favori;
    Button participer, commenter, chatter;
    public static INodeJS iNodeJS;
    int idus;
    CardView det;
    TextView titleevent, date, nbplace, location, price, username, type;
    private AppDataBase database;


    public EvenementAdapter(Context mContext, List<Evenement> mDataa) {
        this.mContext = mContext;
        this.mData = mDataa;
    }


    @NonNull
    @Override
    public EvenementAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_item, parent, false);
        EvenementAdapter.myViewHolder vv = new EvenementAdapter.myViewHolder(v);

        sharedPreferencesUE = parent.getContext().getSharedPreferences("UserEvent", Context.MODE_PRIVATE);
        sharedPreferencesU = parent.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        idus = sharedPreferencesU.getInt("idUser", 0);
        String nomu = sharedPreferencesU.getString("nomUser", "");



        database = AppDataBase.getAppDatabase(context);
        event_list = database.produitDao().getAll();

        return vv;
    }

    @Override
    public void onBindViewHolder(@NonNull EvenementAdapter.myViewHolder holder, int position) {
        final Evenement evenement = mData.get(position);

        favori = holder.favori;

        if (database.produitDao().check_item(evenement.getId_evenement()) != 0) {
            favori.setImageResource(R.drawable.logol);
        }


        titleevent.setText(evenement.getNom_evenement());
        date.setText(evenement.getDate_evenement());
        price.setText(evenement.getPrice_evenement() + " DT");
        location.setText(evenement.getLocation_evenement());
        type.setText(evenement.getType_evenement());
        nbplace.setText("Nombre de places " + evenement.getNbplace_evenement());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView pr_title;
        public ImageView background_img;
        public ImageView favori;

        public myViewHolder(@NonNull final View itemView) {
            super(itemView);
            sharedPreferencesV = itemView.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
            final int id_ev = sharedPreferencesV.getInt("id_evenement", 0);
            background_img = itemView.findViewById(R.id.background_img);
            date = itemView.findViewById(R.id.date);
            favori = itemView.findViewById(R.id.favori);
            type = itemView.findViewById(R.id.typeh);
            participer = itemView.findViewById(R.id.participer);
            commenter = itemView.findViewById(R.id.commenter);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            titleevent = itemView.findViewById(R.id.titleevent);
            nbplace = itemView.findViewById(R.id.nbplacecard);
            chatter = itemView.findViewById(R.id.chat);
            iNodeJS = RetrofitClient.getInstance().create(INodeJS.class);

            username = itemView.findViewById(R.id.username);
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), DetailsActivity1.class));

                }
            });

            commenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), MapActivity.class));
                }
            });
            chatter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), MessageActivity.class));

                }
            });


            participer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    compositeDisposable.add(iNodeJS.addParticipant(idus, id_ev)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s)
                                        throws Exception {
                                    Call<Evenement>call=iNodeJS.updateEv(id_ev);
                                    call.enqueue(new Callback<Evenement>() {
                                        @Override
                                        public void onResponse(Call<Evenement> call, Response<Evenement> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<Evenement> call, Throwable t) {

                                        }
                                    });
                                    participer.setText("deja participer!");
                                    System.out.println("evenement ajouté");
                                }
                            })
                    );

                }


            });


            favori.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database = AppDataBase.getAppDatabase(v.getContext());
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) {
                        //sharedPreferences = v.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
                        final Evenement ce = mData.get(position);
                        if (database.produitDao().check_item(ce.getId_evenement()) != 0) {
                            Toast.makeText(v.getContext(), " already exists", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                database.produitDao().insertOne(ce);
                                favori.setImageResource(R.drawable.tfb);
                                Toast.makeText(v.getContext(), "inserted", Toast.LENGTH_LONG).show();


                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }

                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    database = AppDataBase.getAppDatabase(v.getContext());
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) {
                        sharedPreferences = v.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
                        sharedPreferencesUE = v.getContext().getSharedPreferences("UserEvent", Context.MODE_PRIVATE);
                        Evenement ce = mData.get(position);


                        System.out.println();
                        int aa = ce.getId_user();
                        System.out.println(aa + "aaaaaaa");
                        // test(aa);


                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("evenement_nom", ce.getNom_evenement());
                        editor.putString("evenement_type", ce.getType_evenement());
                        editor.putInt("evenement_prix", ce.getPrice_evenement());
                        editor.putString("evenement_desc", ce.getDescription_evenement());
                        editor.putString("evenement_date", ce.getDate_evenement());
                        editor.putInt("id_evenement", ce.getId_evenement());
                        editor.putInt("id_user", ce.getId_user());
                        editor.putInt("evenement_infoline", ce.getInfoline_evenement());
                        editor.putInt("evenement_nbplace", ce.getNbplace_evenement());


                        editor.apply();
                        //det.setVisibility(View.VISIBLE);
                        //test.setText(ce.getNom_evenement());


                    }


                    itemView.getContext().startActivity(new Intent(itemView.getContext(), DetailsActivity.class));


                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });


        }
    }


    public void addParticipant(int id_user, int id_evenement) {


        compositeDisposable.add(iNodeJS.addParticipant(id_user, id_evenement)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        participer.setText("deja participé!");
                        System.out.println("evenement ajouté");
                    }
                })
        );

    }


}







