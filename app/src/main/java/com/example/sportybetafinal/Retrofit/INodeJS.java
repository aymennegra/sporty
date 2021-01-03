package com.example.sportybetafinal.Retrofit;


import com.example.sportybetafinal.Entities.Evenement;
import com.example.sportybetafinal.Entities.Participants;
import com.example.sportybetafinal.Entities.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface INodeJS {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser (@Field("email") String email,
                                     @Field("name") String name,
                                     @Field("prenom") String prenom,
                                     @Field("tel_user") String tel_user,
                                     @Field("password") String password);
    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser (@Field("email") String email,
                                  @Field("password") String password);

    @GET("/user/{email}")
    Call<User> getUser(@Path("email") String email);



    @Multipart
    @POST("/upload")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name);

    @GET("/uploads/{upload}")
    Call<ResponseBody> getImage(@Path("upload") String n);



    @POST("/participant/add/")
    @FormUrlEncoded
    Observable<String> addParticipant (@Field("id_user") int id_user,
                                  @Field("id_evenement") int id_evenement);


    @GET("/GetEvenementUser/{id_user}")
    Call<List<Evenement>> getparticipList(@Path("id_user") int id_user);

    @GET("/GetParticipants")
    Call<List<Participants>> getParticipantList();


    @POST("/evenement/add")
    @FormUrlEncoded
    Observable<String> addEvenement (@Field("nom_evenement") String nom_evenement,
                                     @Field("desc_evenement") String description_evenement,
                                     @Field("type_evenement") String type_evenement,
                                     @Field("location_evenement") String location_evenement,
                                     @Field("price_evenement") int price_evenement,
                                     @Field("infoline_evenement") String infoline_evenement,
                                     @Field("date_evenement") String date_evenement,
                                     @Field("id_user") int id_user,
                                     @Field("nbplace_evenement") int nbplace_evenement);

    @GET("/GetEvents/")
    Call<List<Evenement>> getEventsList();

    @PUT("/evenement/update/{id_evenement}")
    Call<Evenement> updateEv(@Path("id_evenement") int id_evenement);


    @DELETE("/particip/delete/{id_evenement}")
    Call<Participants> deleteparticipant(@Path("id_evenement") int id_evenement);













}
