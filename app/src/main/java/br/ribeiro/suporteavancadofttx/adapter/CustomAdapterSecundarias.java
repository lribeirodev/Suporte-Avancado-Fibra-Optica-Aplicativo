package br.ribeiro.suporteavancadofttx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.ribeiro.suporteavancadofttx.R;

public class CustomAdapterSecundarias extends RecyclerView.Adapter<CustomAdapterSecundarias.CustomViewHolderSecundarias> {

    private String[] secundaria_selecionada;

    public CustomAdapterSecundarias(String[] secundaria_selecionada){
        this.secundaria_selecionada = secundaria_selecionada;
    }

    @NonNull
    @Override
    public CustomViewHolderSecundarias onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.secundarias_layout,parent,false);
        return new CustomViewHolderSecundarias(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderSecundarias holder, int position) {

        String[] data = secundaria_selecionada[position].split(" ");
        String anilha_numero = data[0];
        String secundaria_numero = data[1];

        secundaria_numero = secundaria_numero.replace("SEC.","");
        anilha_numero = anilha_numero.replace("A.","");

        holder.textSecundaria_Layout.setText(secundaria_numero);
        holder.textAnilha_Layout.setText(anilha_numero);
    }

    @Override
    public int getItemCount() {
        return secundaria_selecionada.length;
    }

    public class CustomViewHolderSecundarias extends RecyclerView.ViewHolder{

        private TextView textAnilha_Layout,textSecundaria_Layout;

        public CustomViewHolderSecundarias(@NonNull View itemView) {
            super(itemView);

            textAnilha_Layout = (TextView) itemView.findViewById(R.id.textAnilha_Layout);
            textSecundaria_Layout = (TextView) itemView.findViewById(R.id.textSecundaria_Layout);

        }
    }

}
