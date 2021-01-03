package com.example.sportybetafinal.Utils.dao;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sportybetafinal.Entities.Evenement;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertOne(Evenement evenement);
    @Query("SELECT * FROM evenement WHERE id_evenement=:id")
    int check_item(int id);
    @Delete
    void delete(Evenement produit);
    @Query("SELECT * FROM evenement")
    List<Evenement> getAll();

}
