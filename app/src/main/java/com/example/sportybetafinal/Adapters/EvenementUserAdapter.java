package com.example.sportybetafinal.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.coretal.home.ModifierEvenement;
import com.example.sportybetafinal.Entities.Evenement;
import com.example.sportybetafinal.Entities.Participants;
import com.example.sportybetafinal.R;
import com.example.sportybetafinal.Retrofit.INodeJS;
import com.example.sportybetafinal.Retrofit.RetrofitClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EvenementUserAdapter extends RecyclerView.Adapter<EvenementUserAdapter.myViewHolder> {

    Context mContext;
    private List<Evenement> mData;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences, sharedPreferencesUE,sharedPreferencesV;
    TextView titre, type, location, prix, date;
    ImageView bk;
    INodeJS myAPI;
    public static INodeJS iNodeJS;
    Button annuler;


    public EvenementUserAdapter(Context mContext, List<Evenement> mDataa) {
        this.mContext = mContext;
        this.mData = mDataa;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_eventuser, parent, false);
        myViewHolder vv = new myViewHolder(v);
        return vv;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final Evenement evenement = mData.get(position);
        titre.setText(evenement.getNom_evenement());
        type.setText(evenement.getType_evenement());
        location.setText(evenement.getLocation_evenement());
        prix.setText(evenement.getPrice_evenement() + "DT");
        date.setText(evenement.getDate_evenement());

        //titre.setText(evenement.getNom_evenement());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView pr_title;
        public ImageView background_img;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            background_img = itemView.findViewById(R.id.background_img);
            titre = itemView.findViewById(R.id.title_p);
            type = itemView.findViewById(R.id.type_p);
            location = itemView.findViewById(R.id.location_p);
            prix = itemView.findViewById(R.id.price_p);
            date = itemView.findViewById(R.id.date_p);
            annuler=itemView.findViewById(R.id.annuler);
            iNodeJS = RetrofitClient.getInstance().create(INodeJS.class);

            sharedPreferences = itemView.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
            int idUser = sharedPreferences.getInt("idUser", 0);
            sharedPreferencesUE = itemView.getContext().getSharedPreferences("UserEvent", Context.MODE_PRIVATE);
            int idUE = sharedPreferencesUE.getInt("idUser", 0);
            sharedPreferencesV = itemView.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
            final int id_ev = sharedPreferencesV.getInt("id_evenement", 0);



            annuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<Participants>call=iNodeJS.deleteparticipant(id_ev);
                    call.enqueue(new Callback<Participants>() {
                        @Override
                        public void onResponse(Call<Participants> call, Response<Participants> response) {

                        }

                        @Override
                        public void onFailure(Call<Participants> call, Throwable t) {

                        }
                    });

                }
            });

            if (idUser == idUE) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition(); // gets item position
                        if (position != RecyclerView.NO_POSITION) {
                            sharedPreferences = v.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
                            Evenement ce = mData.get(position);
                            String aa = ce.getNom_evenement();
                            int a = ce.getId_evenement();
                            System.out.println(a + "aaaa");


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("evenement_nom", ce.getNom_evenement());
                            editor.putString("evenement_type", ce.getType_evenement());
                            editor.putInt("evenement_prix", ce.getPrice_evenement());
                            editor.putString("evenement_desc", ce.getDescription_evenement());
                            editor.putString("evenement_date", ce.getDate_evenement());
                            editor.putString("location_evenement", ce.getLocation_evenement());
                            editor.putInt("evenement_infoline", ce.getInfoline_evenement());
                            editor.putInt("userIdE", ce.getId_user());
                            editor.putInt("eventId", ce.getId_evenement());


                            editor.apply();
                            //det.setVisibility(View.VISIBLE);
                            //test.setText(ce.getNom_evenement());


                            // Fragment fragg = new ModifierEvenement();
                            // ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.details, fragg).commit();


                        }

                    }
                });
            }


        }
    }
}
