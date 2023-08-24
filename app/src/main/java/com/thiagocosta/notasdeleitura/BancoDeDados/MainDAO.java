package com.thiagocosta.notasdeleitura.BancoDeDados;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.thiagocosta.notasdeleitura.Modelos.Notas;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert (Notas notas);

    @Query("SELECT * FROM notas ORDER BY id DESC")
    List<Notas> getAll();

    @Query("UPDATE notas SET titulo = :titulo, notas = :notas WHERE ID = :id")
    void update (int id, String titulo, String notas);

    @Delete
    void delete (Notas notas);

    @Query("UPDATE notas SET pin = :pin WHERE ID = :id")
    void pin (int id, boolean pin);

    /* @RecyclerView.ItemAnimator.AdapterChanges ("UPDATE notas SET color = :color WHERE ID = :id")
    void color (int id, boolean color);

     */

} // fim class
