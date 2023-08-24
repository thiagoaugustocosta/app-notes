package com.thiagocosta.notasdeleitura.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thiagocosta.notasdeleitura.LeitorDeCliquesNasNotas;
import com.thiagocosta.notasdeleitura.Modelos.Notas;
import com.thiagocosta.notasdeleitura.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdaptadorDaListaDeNotas extends RecyclerView.Adapter<SuporteParaVerNotas> {
    Context context;
    List<Notas> list;
    LeitorDeCliquesNasNotas leitor;

    public AdaptadorDaListaDeNotas(Context context, List<Notas> list, LeitorDeCliquesNasNotas leitor) {
        this.context = context;
        this.list = list;
        this.leitor = leitor;
    }

    @NonNull
    @Override
    public SuporteParaVerNotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SuporteParaVerNotas(LayoutInflater.from(context).inflate(R.layout.notas_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SuporteParaVerNotas holder, int position) {
        holder.textView_titulo.setText(list.get(position).getTitulo());
        holder.textView_titulo.setSelected(true);

        holder.textView_notas.setText(list.get(position).getNotas());

        holder.textView_data.setText(list.get(position).getData());
        holder.textView_data.setSelected(true);

            if (list.get(position).isPin()) {
                holder.imageView_pin.setImageResource(R.drawable.ic_pin);
            } else {
                holder.imageView_pin.setImageResource(0);
            }

            int color_code = getRandomColor();
            holder.notas_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

            holder.notas_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    leitor.onClick(list.get(holder.getAdapterPosition()));
                }
            });

            holder.notas_container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    leitor.onLongClick(list.get(holder.getAdapterPosition()), holder.notas_container);
                    return true;
                }
            });

    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filtrarLista (List<Notas> listaFiltrada) {
        list = listaFiltrada;
        notifyDataSetChanged();
    }


} // fim class Adaptador



class SuporteParaVerNotas extends RecyclerView.ViewHolder {

    CardView notas_container;
    TextView textView_titulo, textView_notas, textView_data;
    ImageView imageView_pin;
    public SuporteParaVerNotas(@NonNull View itemView) {
        super(itemView);

        notas_container = itemView.findViewById(R.id.notas_container);
        textView_titulo = itemView.findViewById(R.id.textView_titulo);
        textView_notas = itemView.findViewById(R.id.textView_notas);
        textView_data = itemView.findViewById(R.id.textView_data);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);

    }


} // fim class Suporte
