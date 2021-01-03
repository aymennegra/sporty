package com.example.sportybetafinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportybetafinal.Entities.Evenement;
import com.example.sportybetafinal.Retrofit.INodeJS;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pubAdapter extends RecyclerView.Adapter<pubAdapter.myViewHolder>  {

    Context mContext;
    private List<Evenement> mData;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    INodeJS myAPI;
    ImageView bk;


    public pubAdapter(Context mContext, List<Evenement> mData, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.mData = mData;
        this.fragmentManager = fragmentManager;
    }


    public pubAdapter(Context mContext, List<Evenement> mDataa) {
        this.mContext = mContext;
        this.mData = mDataa;
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_item,parent,false);
        pubAdapter.myViewHolder vv = new pubAdapter.myViewHolder(v);
        return vv;
    }






    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        final Evenement evenement = mData.get(position);


        //String a = (evenement.getPhoto_evenement());

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myAPI = retrofit.create(INodeJS.class);
        Call<ResponseBody> calll = myAPI.getImage("null");
        calll.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> calll, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        bk.setImageBitmap(bmp);
                    } else {
                        // TODO
                    }
                } else {
                    // TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> calll, Throwable t) {
                // TODO
            }
        });
            TextView titre = holder.pr_title;
              bk = holder.background_img;
            titre.setText(evenement.getNom_evenement());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView profile_photo, background_img;
        public TextView pr_title;


        public myViewHolder(final View itemView) {
            super(itemView);


            profile_photo = itemView.findViewById(R.id.imageUser);
//            background_img = itemView.findViewById(R.id.card_background);
            pr_title = itemView.findViewById(R.id.tv_title);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) {
                sharedPreferences = v.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
                Evenement ce = mData.get(position);
                String aa = ce.getNom_evenement();



                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("evenement_nom",ce.getNom_evenement());
                editor.putString("evenement_type",ce.getType_evenement());
                editor.putInt("evenement_price",ce.getPrice_evenement());
                editor.putInt("evenement_nbplace",ce.getNbplace_evenement());
                editor.apply();





                 Fragment fragg = new DetailEvent();
                ((AppCompatActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.navigation_home, fragg).commit();


            }
        }
    }}
