package br.ribeiro.suporteavancadofttx.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import br.ribeiro.suporteavancadofttx.R;
import br.ribeiro.suporteavancadofttx.adapter.CustomAdapterSecundarias;
import br.ribeiro.suporteavancadofttx.adapter.CustomAdapterSplitter;

public class CalculadoraDgoic extends AppCompatActivity {

    private Button buttonVoltar, buttonCalcular, buttonLimpar;
    private EditText textSplitter;
    private RecyclerView listaSplitter, listaSecundarias;
    private Object[][] nova_lista;
    private TableRow tabelaTitulo;
    private TextView textAnilha, textSecundaria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_dgoic);

        buttonVoltar = findViewById(R.id.buttonVoltar_Dgoic);
        buttonCalcular = findViewById(R.id.buttonCalcular_Dgoic);
        buttonLimpar = findViewById(R.id.buttonLimpar_Dgoic);
        textSplitter = findViewById(R.id.editTextPDA_Dgoic);
        listaSplitter = findViewById(R.id.listaSplitter_Dgoic);
        listaSecundarias = findViewById(R.id.listaSecundarias_Dgoic);
        tabelaTitulo = findViewById(R.id.tabelaTitulo);
        textAnilha = findViewById(R.id.textAnilha);
        textSecundaria = findViewById(R.id.textSecundaria);

        LayoutInflater inflater = getLayoutInflater();
        View layout_toast = inflater.inflate(R.layout.toast,(ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text_toast = layout_toast.findViewById(R.id.textToast);
        layout_toast.setBackgroundColor(Color.parseColor("#FF720098"));

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setView(layout_toast);

        textSplitter.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                textSplitter.removeTextChangedListener(this);

                if(s.length() > 0){
                    s.append(",");
                }

                textSplitter.addTextChangedListener(this);

            }
        });

        buttonVoltar.setOnClickListener(v -> {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        });

        buttonLimpar.setOnClickListener(v -> {

            if(textSplitter.getText().length() > 0)
            {
                textSplitter.getText().clear();
                listaSplitter.setAdapter(null);
                listaSecundarias.setAdapter(null);
                tabelaTitulo.setBackgroundColor(Color.parseColor("#FF720098"));
                textAnilha.setTextColor(Color.WHITE);
                textSecundaria.setTextColor(Color.WHITE);

                layout_toast.setBackgroundColor(Color.parseColor("#FF720098"));
                text_toast.setTextColor(Color.WHITE);
                text_toast.setText("LIMPANDO DADOS, AGUARDE...");
            } else {
                layout_toast.setBackgroundColor(Color.RED);
                text_toast.setTextColor(Color.WHITE);
                text_toast.setText("NÃO HÁ DADOS PARA LIMPAR!");
            }

            toast.show();

        });

        buttonCalcular.setOnClickListener(v -> {
            if(textSplitter.length() > 0 && textSplitter.length() < 25) {
                CalculadoraDgoic.this.runOnUiThread(() -> {

                    CustomAdapterSplitter adapter = calcularCoresAnilha();
                    listaSplitter.setAdapter(adapter);

                    adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                        @Override
                        public void onItemRangeChanged(int positionStart, int itemCount) {
                            super.onItemRangeChanged(positionStart, itemCount);

                            CustomAdapterSecundarias adapter = calcularSecundarias(positionStart);
                            listaSecundarias.setAdapter(adapter);

                            tabelaTitulo.setBackgroundColor((Integer) nova_lista[itemCount][3]);
                            textAnilha.setTextColor((Integer) nova_lista[itemCount][2]);
                            textSecundaria.setTextColor((Integer) nova_lista[itemCount][2]);

                            }
                        });
                });

            } else {
                layout_toast.setBackgroundColor(Color.RED);
                text_toast.setTextColor(Color.WHITE);
                text_toast.setText("DADOS INVÁLIDOS,\nVERIFIQUE O CAMPO DO SPLITTER DIGITADO");
                toast.show();
            }
        });

        RecyclerView.LayoutManager layoutManagerSecundarias = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        listaSecundarias.setLayoutManager(layoutManagerSecundarias);
        listaSecundarias.setAdapter(null);

        RecyclerView.LayoutManager layoutManagerSplitter = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        listaSplitter.setLayoutManager(layoutManagerSplitter);
        listaSplitter.setAdapter(null);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode){
                case KeyEvent.KEYCODE_BACK:

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();

                    return true;
            }
        }


        return super.onKeyDown(keyCode, event);
    }


    private CustomAdapterSplitter calcularCoresAnilha(){

        String[] splitters = textSplitter.getText().toString().split(",");
        Object[][] lista_splitter =
        {
                {"VERDE",Color.BLACK,Color.GREEN},
                {"AMARELA",Color.BLACK,Color.YELLOW},
                {"BRANCA",Color.BLACK,Color.WHITE},
                {"AZUL",Color.WHITE,Color.BLUE},
                {"VERMELHO",Color.BLACK,Color.RED},
                {"VIOLETA",Color.WHITE,Color.parseColor("#FF8E007D")},
                {"MARROM",Color.WHITE,Color.parseColor("#FF933D1C")},
                {"ROSA",Color.BLACK,Color.parseColor("#FFFFBDC8")},
                {"PRETO",Color.WHITE,Color.BLACK},
                {"CINZA",Color.BLACK,Color.parseColor("#FF808080")},
                {"LARANJA",Color.BLACK,Color.parseColor("#FFFFA000")},
                {"AQ MAR",Color.WHITE,Color.parseColor("#FF00017F")},
        };

        nova_lista = new Object[splitters.length][4];

        for(int position = 0; position < splitters.length ; position++){
            String splitter_id = "SS"+splitters[position];
            String nome_cor = (String) lista_splitter[position][0];
            int cor_letra = (int) lista_splitter[position][1];
            int cor_fundo = (int) lista_splitter[position][2];

            nova_lista[position][0] = splitter_id;
            nova_lista[position][1] = nome_cor;
            nova_lista[position][2] = cor_letra;
            nova_lista[position][3] = cor_fundo;

        }


        return new CustomAdapterSplitter(nova_lista);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private CustomAdapterSecundarias calcularSecundarias(int index){

        String[] secundaria = {"ZERO",
            "A.1 SEC.1/A.2 SEC.2/A.3 SEC.3/A.4 SEC.4/A.5 SEC.5/A.6 SEC.6/A.7 SEC.7/A.8 SEC.8",
            "A.1 SEC.9/A.2 SEC.10/A.3 SEC.11/A.4 SEC.12/A.5 SEC.13/A.6 SEC.14/A.7 SEC.15/A.8 SEC.16",
            "A.1 SEC.17/A.2 SEC.18/A.3 SEC.19/A.4 SEC.20/A.5 SEC.21/A.6 SEC.22/A.7 SEC.23/A.8 SEC.24",
            "A.1 SEC.25/A.2 SEC.26/A.3 SEC.27/A.4 SEC.28/A.5 SEC.29/A.6 SEC.30/A.7 SEC.31/A.8 SEC.32",
            "A.1 SEC.33/A.2 SEC.34/A.3 SEC.35/A.4 SEC.36/A.5 SEC.37/A.6 SEC.38/A.7 SEC.39/A.8 SEC.40",
            "A.1 SEC.41/A.2 SEC.42/A.3 SEC.43/A.4 SEC.44/A.5 SEC.45/A.6 SEC.46/A.7 SEC.47/A.8 SEC.48",
            "A.1 SEC.49/A.2 SEC.50/A.3 SEC.51/A.4 SEC.52/A.5 SEC.53/A.6 SEC.54/A.7 SEC.55/A.8 SEC.56",
            "A.1 SEC.57/A.2 SEC.58/A.3 SEC.59/A.4 SEC.60/A.5 SEC.61/A.6 SEC.62/A.7 SEC.63/A.8 SEC.64"};

        String[] secundaria_selecionada = secundaria[index].trim().split("/");

        return new CustomAdapterSecundarias(secundaria_selecionada);
    }
}