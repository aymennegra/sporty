package com.example.sportybetafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.sportybetafinal.Entities.User;
import com.example.sportybetafinal.R;
import com.example.sportybetafinal.Retrofit.INodeJS;
import com.example.sportybetafinal.Retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import android.os.Bundle;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText email,password;
    Button btn_register,btn_login;
    SharedPreferences sharedPreferences;


//Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        loadClientData();

        initRetrofitClient();


         //log = findViewById(R.id.button6);


        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_particip)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);






        }



    private void initRetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        myAPI = new Retrofit.Builder().baseUrl("http://10.0.2.2:3000").client(client).build().create(INodeJS.class);
    }

    public void loadClientData(){
        sharedPreferences =getApplicationContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String idus = sharedPreferences.getString("EmailUser", "");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myAPI = retrofit.create(INodeJS.class);
        Call<User> call = myAPI.getUser(idus);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("idUser",user.getId());
                editor.putInt("telUser",user.getTel_user());
                editor.putString("nomUser",user.getName());
                editor.putString("prenomUser",user.getPrenom());
                editor.putString("EmailUser",user.getEmail());
                editor.putString("imageUser",user.getImage_user());
                editor.apply();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }





}
