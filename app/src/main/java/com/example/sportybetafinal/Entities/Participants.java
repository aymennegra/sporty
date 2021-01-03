package com.example.sportybetafinal.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "participants")
public class Participants {


    public Participants(int id_participant, int id_user, int id_evenement) {
        this.id_participant = id_participant;
        this.id_user = id_user;
        this.id_evenement = id_evenement;
    }
    @PrimaryKey(autoGenerate = true)
    private int id_participant;
    @ColumnInfo(name = "id_user")
    private int id_user;
    @ColumnInfo(name = "id_evenement")
    private int id_evenement;


    public int getId_participant() {
        return id_participant;
    }

    public void setId_participant(int id_participant) {
        this.id_participant = id_participant;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public Participants() {
    }



}
