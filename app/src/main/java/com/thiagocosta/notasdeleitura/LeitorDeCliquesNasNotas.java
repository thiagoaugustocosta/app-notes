package com.thiagocosta.notasdeleitura;

import androidx.cardview.widget.CardView;

import com.thiagocosta.notasdeleitura.Modelos.Notas;

public interface LeitorDeCliquesNasNotas {
    void onClick (Notas notas);
    void onLongClick (Notas notas, CardView cardView);


} // fim class
