package com.example.sportybetafinal.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "evenement")
public class Evenement {



    public Evenement(int id_evenement, String nom_evenement,String description_evenement, String type_evenement, String location_evenement, int price_evenement, int infoline_evenement, String date_evenement, int id_user,int nbplace_evenement) {
        this.id_evenement = id_evenement;
        this.nom_evenement = nom_evenement;
        this.description_evenement = description_evenement;
        this.type_evenement = type_evenement;
        this.location_evenement = location_evenement;
        this.price_evenement = price_evenement;
        this.infoline_evenement = infoline_evenement;
        this.date_evenement = date_evenement;
        this.id_user = id_user;
        this.nbplace_evenement = nbplace_evenement;
    }





    @PrimaryKey(autoGenerate = true)
    @SerializedName("id_evenement")
    @Expose
    private int id_evenement;
    @SerializedName("nom_evenement")
    private String nom_evenement;
    @SerializedName("type_evenement")
    private String type_evenement;
    @SerializedName("location_evenement")
    private String location_evenement;
    @SerializedName("description_evenement")
    private String description_evenement;
    @SerializedName("infoline_evenement")
    private int infoline_evenement;
    @SerializedName("price_evenement")
    private int price_evenement;
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("date_evenement")
    private String date_evenement;
    @SerializedName("nbplace_evenement")
    private int nbplace_evenement;

    public Evenement() {

    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public String getNom_evenement() {
        return nom_evenement;
    }

    public void setNom_evenement(String nom_evenement) {
        this.nom_evenement = nom_evenement;
    }

    public String getType_evenement() {
        return type_evenement;
    }

    public void setType_evenement(String type_evenement) {
        this.type_evenement = type_evenement;
    }

    public String getLocation_evenement() {
        return location_evenement;
    }

    public void setLocation_evenement(String location_evenement) {
        this.location_evenement = location_evenement;
    }

    public String getDescription_evenement() {
        return description_evenement;
    }

    public void setDescription_evenement(String description_evenement) {
        this.description_evenement = description_evenement;
    }

    public int getInfoline_evenement() {
        return infoline_evenement;
    }

    public void setInfoline_evenement(int infoline_evenement) {
        this.infoline_evenement = infoline_evenement;
    }

    public int getPrice_evenement() {
        return price_evenement;
    }

    public void setPrice_evenement(int price_evenement) {
        this.price_evenement = price_evenement;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDate_evenement() {
        return date_evenement;
    }

    public void setDate_evenement(String date_evenement) {
        this.date_evenement = date_evenement;
    }

    public int getNbplace_evenement() {
        return nbplace_evenement;
    }

    public void setNbplace_evenement(int nbplace_evenement) {
        this.nbplace_evenement = nbplace_evenement;
    }

}
