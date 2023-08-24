package com.thiagocosta.notasdeleitura;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thiagocosta.notasdeleitura.Adaptadores.AdaptadorDaListaDeNotas;
import com.thiagocosta.notasdeleitura.BancoDeDados.RoomDB;
import com.thiagocosta.notasdeleitura.Modelos.Notas;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    AdaptadorDaListaDeNotas adaptadorDaListaDeNotas;
    List<Notas> notas = new ArrayList<>();
    RoomDB bancoDeDados;
    FloatingActionButton fab_adicionar;
    SearchView searchView_inicio;
    Notas notaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_inicio);
        fab_adicionar = findViewById(R.id.fab_adicionar);
        searchView_inicio = findViewById(R.id.searchView_inicio);

        bancoDeDados = RoomDB.getInstance(this);
        notas = bancoDeDados.mainDAO().getAll();

        atualizarRecycler(notas);

        fab_adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent tela = new Intent(MainActivity.this, PegadorDeNotas.class);
                startActivityForResult(tela, 101);

            }
        });

        searchView_inicio.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

    }

    private void filter(String newText) {
        List<Notas> listaFiltrada = new ArrayList<>();
            for (Notas notaUnica : notas) {
                if (notaUnica.getTitulo().toLowerCase().contains(newText.toLowerCase())
                || notaUnica.getNotas().toLowerCase().contains(newText.toLowerCase())) {
                    listaFiltrada.add(notaUnica);
                }
            }

            adaptadorDaListaDeNotas.filtrarLista(listaFiltrada);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Notas novas_notas = (Notas) data.getSerializableExtra("nota");
                bancoDeDados.mainDAO().insert(novas_notas);
                notas.clear();
                notas.addAll(bancoDeDados.mainDAO().getAll());
                adaptadorDaListaDeNotas.notifyDataSetChanged();
            }
        }

        else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                Notas novas_notas = (Notas) data.getSerializableExtra("nota");
                bancoDeDados.mainDAO().update(novas_notas.getID(), novas_notas.getTitulo(), novas_notas.getNotas());
                notas.clear();
                notas.addAll(bancoDeDados.mainDAO().getAll());
                adaptadorDaListaDeNotas.notifyDataSetChanged();
            }
        }

    }

    private void atualizarRecycler(List<Notas> notas) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adaptadorDaListaDeNotas = new AdaptadorDaListaDeNotas(MainActivity.this, notas, leitorDeCliquesNasNotas);
        recyclerView.setAdapter(adaptadorDaListaDeNotas);

    }

    private final LeitorDeCliquesNasNotas leitorDeCliquesNasNotas = new LeitorDeCliquesNasNotas() {
        @Override
        public void onClick(Notas notas) {
            Intent tela = new Intent(MainActivity.this, PegadorDeNotas.class);
            tela.putExtra("velha_nota", notas);
            startActivityForResult(tela, 102);

        }

        @Override
        public void onLongClick(Notas notas, CardView cardView) {
            notaSelecionada = new Notas();
            notaSelecionada = notas;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pin:
                if (notaSelecionada.isPin()) {
                    bancoDeDados.mainDAO().pin(notaSelecionada.getID(), false);
                    Toast.makeText(MainActivity.this, "Tirar pin!", Toast.LENGTH_SHORT).show();
                } else {
                    bancoDeDados.mainDAO().pin(notaSelecionada.getID(), true);
                    Toast.makeText(MainActivity.this, "Pin!", Toast.LENGTH_SHORT).show();
                }

                notas.clear();
                notas.addAll(bancoDeDados.mainDAO().getAll());
                adaptadorDaListaDeNotas.notifyDataSetChanged();
                return true;

            case R.id.delete:
                bancoDeDados.mainDAO().delete(notaSelecionada);
                notas.remove(notaSelecionada);
                adaptadorDaListaDeNotas.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Nota Deletada!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
} // fim class