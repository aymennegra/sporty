package com.example.sportybetafinal;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sportybetafinal.Retrofit.INodeJS;
import com.example.sportybetafinal.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AjouterEvenement extends Fragment {


        INodeJS myAPI;
        SeekBar slider;
        TextView diffV;
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        EditText nom,description,location,price,infoline,date,nbplace;
        Button add;
        SharedPreferences sharedPreferences;
        int id_user;
        DatePickerDialog.OnDateSetListener dateSetListener1,dateSetListener2;
        private static final String[] cats = {"Football", "Basketball","VolleyBall","HandBall","Tennis"};
        //upload imgae
        Uri picUri;
        private ArrayList<String> permissionsToRequest;
        private ArrayList<String> permissionsRejected = new ArrayList<>();
        private ArrayList<String> permissions = new ArrayList<>();
        private final static int ALL_PERMISSIONS_RESULT = 107;
        private final static int IMAGE_RESULT = 200;
        public Button fabCamera, fabUpload;
        Bitmap mBitmap;
        TextView textView;
        ImageView imageView;
        Spinner type;

        public AjouterEvenement() {

        }

        @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_add, container, false);

                sharedPreferences = getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
                id_user = sharedPreferences.getInt("idUser", 0);
                // System.out.println(idu+"");
                nom = rootView.findViewById(R.id.name2);
                description = rootView.findViewById(R.id.desc2);
                type = rootView.findViewById(R.id.cat);
                location = rootView.findViewById(R.id.location2);
                price = rootView.findViewById(R.id.price2);
                infoline = rootView.findViewById(R.id.infoline2);
                date = rootView.findViewById(R.id.date2);
                nbplace = rootView.findViewById(R.id.nbplace);
                add = rootView.findViewById(R.id.addE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),  android.R.layout.simple_spinner_item, cats);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                type.setAdapter(adapter);

                //Init API
                Retrofit retrofit = RetrofitClient.getInstance();
                myAPI = retrofit.create(INodeJS.class);

                add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if (type.getSelectedItem().toString()=="Football"){
                                        String img="f";
                                }
                                addEvenement(nom.getText().toString(),description.getText().toString(),type.getSelectedItem().toString(),location.getText().toString(),Integer.parseInt(price.getText().toString()),
                                        infoline.getText().toString(),date.getText().toString(),id_user,Integer.parseInt(nbplace.getText().toString()));
                                Intent intent = new Intent(Intent.ACTION_INSERT);
                                intent.setData(CalendarContract.Events.CONTENT_URI);
                                intent.putExtra(CalendarContract.Events.TITLE, nom.getText().toString());
                                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                                intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                                intent.putExtra(CalendarContract.Events.ALL_DAY,true);
                                intent.putExtra(Intent.EXTRA_EMAIL,"aymen.negra@esprit.tn");
                                startActivity(intent);

                        }

                });

                Calendar calendar=Calendar.getInstance();
                final int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH);
                final int day=calendar.get(Calendar.DAY_OF_MONTH);

                date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),
                                        dateSetListener1,year,month,day);
                                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                                datePickerDialog.getWindow();
                                datePickerDialog.show();
                        }
                });
                dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                month +=1;
                                String d=day + "/" + month + "/" + year;
                                date.setText(d);
                        }
                };



                return rootView;
        }

        private void addEvenement(final String nom, final String description, final String type,  final String location, final int price,
                                  final String infoline, final String date,  int id_user , int nbplace) {


                System.out.println(id_user);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                Retrofit retrofit = builder.build();
                myAPI = retrofit.create(INodeJS.class);

                compositeDisposable.add(myAPI.addEvenement(nom,description,type,location,price,infoline,date,id_user,nbplace)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                        Toast.makeText(getContext(),"evenement ajouté",Toast.LENGTH_SHORT).show();
                                        System.out.println("evenement ajouté");
                                }
                        })
                );

        }
        public static AjouterEvenement newInstance() {

                final AjouterEvenement fragment = new AjouterEvenement();


                return fragment;
        }

}

