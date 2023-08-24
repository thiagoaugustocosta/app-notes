package com.thiagocosta.notasdeleitura;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thiagocosta.notasdeleitura.Modelos.Notas;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PegadorDeNotas extends AppCompatActivity {
    EditText editText_titulo, editText_notas;
    ImageView imageView_salvar;
    Notas notas;
    boolean aVelhaNota = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegador_de_notas);

        editText_notas = findViewById(R.id.editText_notas);
        editText_titulo = findViewById(R.id.editText_titulo);
        imageView_salvar = findViewById(R.id.imageView_salvar);

        notas = new Notas();
            try {
                notas = (Notas) getIntent().getSerializableExtra("velha_nota");
                editText_titulo.setText(notas.getTitulo());
                editText_notas.setText(notas.getNotas());
                aVelhaNota = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        imageView_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = editText_titulo.getText().toString();
                String descricao = editText_notas.getText().toString();

                if (descricao.isEmpty()) {
                    Toast.makeText(PegadorDeNotas.this, "Adicione suas notas!", Toast.LENGTH_SHORT).show();
                    return;

                }

                SimpleDateFormat formatador = new SimpleDateFormat("EEE , d MMM yyyy HH:mm a");
                Date data = new Date();

                if (!aVelhaNota) {
                    notas = new Notas();
                }

                notas.setTitulo(titulo);
                notas.setNotas(descricao);
                notas.setData(formatador.format(data));

                Intent tela = new Intent();
                tela.putExtra("nota", notas);
                setResult(Activity.RESULT_OK, tela);
                finish();

            }
        });

    }



} // fim class