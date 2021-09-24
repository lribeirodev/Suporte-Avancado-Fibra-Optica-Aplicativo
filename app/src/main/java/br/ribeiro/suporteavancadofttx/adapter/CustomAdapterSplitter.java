package br.ribeiro.suporteavancadofttx.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.ribeiro.suporteavancadofttx.R;

public class CustomAdapterSplitter extends RecyclerView.Adapter<CustomAdapterSplitter.CustomViewHolderSplitter> {

    private Object[][] lista_splitter;

    public CustomAdapterSplitter(Object[][] lista_splitter){
        this.lista_splitter = lista_splitter;
    }

    @NonNull
    @Override
    public CustomViewHolderSplitter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.botao_splitter,parent,false);
        return new CustomViewHolderSplitter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderSplitter holder, int position) {
        String splitter = (String) lista_splitter[position][0];
        String nome_cor = (String) lista_splitter[position][1];
        int cor_letra = (int) lista_splitter[position][2];
        int cor_fundo = (int) lista_splitter[position][3];

        holder.bind(position);
        holder.textNomeCor.setText(nome_cor);
        holder.buttonSplitter.setText(splitter);
        holder.buttonSplitter.setTextColor(cor_letra);
        holder.buttonSplitter.setBackgroundColor(cor_fundo);
    }

    @Override
    public int getItemCount() {
        return lista_splitter.length;
    }

    public class CustomViewHolderSplitter extends RecyclerView.ViewHolder{

        private Button buttonSplitter;
        private TextView textNomeCor;
        private int indice;

        public CustomViewHolderSplitter(@NonNull View itemView) {
            super(itemView);

            buttonSplitter = itemView.findViewById(R.id.buttonSplitter);
            textNomeCor = itemView.findViewById(R.id.textNomeCor);

            buttonSplitter.setOnClickListener(v -> {
                Integer index = Integer.valueOf(((String) lista_splitter[getAdapterPosition()][0]).replace("SS",""));
                notifyItemRangeChanged(index,indice);
            });

        }

        void bind(int indice){
            this.indice = indice;
        }

    }

}


