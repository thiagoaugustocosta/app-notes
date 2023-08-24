package com.thiagocosta.notasdeleitura.BancoDeDados;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thiagocosta.notasdeleitura.Modelos.Notas;

@Database(entities = Notas.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB bancodedados;
    private static String BANCODEDADOS_NOME = "Notas de Leitura";
    public synchronized static RoomDB getInstance(Context context){
        if(bancodedados == null){
            bancodedados = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, BANCODEDADOS_NOME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return bancodedados;
    }

public abstract MainDAO mainDAO();

} // fim class
