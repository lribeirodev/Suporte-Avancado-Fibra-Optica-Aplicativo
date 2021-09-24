package br.ribeiro.suporteavancadofttx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ribeiro.suporteavancadofttx.R;

public class CalcularPrumada extends AppCompatActivity {

    private Button buttonVoltar_Prumada, buttonCalcular_Prumada, buttonLimpar_Prumada;
    private EditText editTextNumero_Prumada;
    private TextView textViewCabo_Prumada, textViewCorTubo_Prumada, textViewCorFibra_Prumada, textViewBandejaDGOI_Prumada, textViewCaixa_Prumada;
    private Toast toast;
    private View layout_toast;
    private TextView text_toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_prumada);

        buttonCalcular_Prumada = findViewById(R.id.buttonCalcular_Prumada);
        buttonLimpar_Prumada = findViewById(R.id.buttonLimpar_Prumada);
        buttonVoltar_Prumada = findViewById(R.id.buttonVoltar_Prumada);
        editTextNumero_Prumada = findViewById(R.id.editTextNumero_Prumada);
        textViewCabo_Prumada = findViewById(R.id.textViewCabo_Prumada);
        textViewCorTubo_Prumada = findViewById(R.id.textViewCorTubo_Prumada);
        textViewCorFibra_Prumada = findViewById(R.id.textViewCorFibra_Prumada);
        textViewBandejaDGOI_Prumada = findViewById(R.id.textViewBandejaDGOI_Prumada);
        textViewCaixa_Prumada = findViewById(R.id.textViewCaixa_Prumada);

        LayoutInflater inflater = getLayoutInflater();
        layout_toast = inflater.inflate(R.layout.toast,(ViewGroup) findViewById(R.id.toast_layout_root));
        text_toast = layout_toast.findViewById(R.id.textToast);
        layout_toast.setBackgroundColor(Color.parseColor("#FF720098"));

        toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setView(layout_toast);

        buttonVoltar_Prumada.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

        buttonCalcular_Prumada.setOnClickListener(v -> {

            CalcularPrumada.this.runOnUiThread(() -> {
                try {

                    Integer x = Integer.valueOf(editTextNumero_Prumada.getText().toString());

                    if(x > 144 || x <= 0){
                        throw new Exception();
                    } else{
                        calcular_prumada_etapa1(editTextNumero_Prumada.getText().toString());
                    }

                } catch(Exception e){

                    layout_toast.setBackgroundColor(Color.RED);
                    text_toast.setText("DADOS INVÁLIDOS");
                    toast.show();

                }
            });

        });

        buttonLimpar_Prumada.setOnClickListener(v->{
            limpar(1);
        });

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

    private void calcular_prumada_etapa1(String num_prumada) {

        String[] coresTuboRise = {"" , "VERDE" , "AMARELO"
                , "BRANCO" , "AZUL" , "VERMELHO" , "VIOLETA" , "MARROM" , "ROSA"};

        String[] coresRiseFibra = {"" , "VIOLETA" , "VERMELHA" , "AZUL" , "BRANCA" , "AMARELA" , "VERDE"};

        Integer cabo = 1, bandeja = 0, caixa = 4;
        String respTubo = "", respFibra = "";

        Integer o = Integer.valueOf(num_prumada);

        // VALIDAR CABO E NIVELAR NÚMERAÇÃO
        if (o >= 49 && o <= 96) {
            o = o - 48;
            cabo = 2;
            caixa = 8;
        } else if (o >= 97 && o <= 144) {
            o = o - 96;
            cabo = 3;
            caixa = 12;
        }

        // VALIDAR TUBO, CAIXA E BANDEJA PARTE 2
        if(o >= 1 && o < 1+6){
            respTubo = coresTuboRise[1];
            bandeja = 1;
            caixa -=3;
        }else if(o >= 7 && o < 7+6){
            respTubo = coresTuboRise[2];
            bandeja = 1;
            caixa-=3;
        }else if(o >= 13 && o < 13+6){
            respTubo = coresTuboRise[3];
            bandeja = 2;
            caixa-=2;
        }else if(o >= 19 && o < 19+6){
            respTubo = coresTuboRise[4];
            bandeja = 2;
            caixa-=2;
        }else if(o >= 25 && o < 25+6){
            respTubo = coresTuboRise[5];
            bandeja =3;
            caixa-=1;
        }else if(o >= 31 && o < 31+6){
            respTubo = coresTuboRise[6];
            bandeja = 3;
            caixa-=1;
        }else if(o >= 37 && o < 37+6){
            respTubo = coresTuboRise[7];
            bandeja = 4;
        }else if(o >= 43 && o < 43+6){
            respTubo = coresTuboRise[8];
            bandeja = 4;
        }

        // ENCONTRAR COR DA FIBRA
        o = Integer.valueOf(num_prumada);

        if(o >= 49 && o <= 96){
            o = o - 48;
        }else if(o >= 97 && o <= 144){
            o = o - 96;
        }

        Integer x = 0;

        if(o >= 1 && o < 1+6){
            x = 1+6 - o;
        }else if(o >= 7 && o < 7+6){
            x = 7+6 - o;
        }else if(o >= 13 && o < 13+6){
            x = 13+6 - o;
        }else if(o >= 19 && o < 19+6){
            x = 19+6 - o;
        }else if(o >= 25 && o < 25+6){
            x = 25+6 - o;
        }else if(o >= 31 && o < 31+6){
            x = 31+6 - o;
        }else if(o >= 37 && o < 37+6){
            x = 37+6 - o;
        }else if(o >= 43 && o < 43+6){
            x = 43+6 - o;
        }

        o = x;

        switch(o){
            case 1: respFibra = coresRiseFibra[1]; break;
            case 2: respFibra = coresRiseFibra[2]; break;
            case 3: respFibra = coresRiseFibra[3]; break;
            case 4: respFibra = coresRiseFibra[4]; break;
            case 5: respFibra = coresRiseFibra[5]; break;
            case 6: respFibra = coresRiseFibra[6]; break;
        }

        if (cabo > 0) textViewCabo_Prumada.setText(String.valueOf(cabo));
        if (caixa > 0) textViewCaixa_Prumada.setText(String.valueOf(caixa));
        if (bandeja > 0) textViewBandejaDGOI_Prumada.setText(String.valueOf(bandeja));
        if (respTubo.length() > 0) textViewCorTubo_Prumada.setText(respTubo); textViewCorTubo_Prumada.setBackgroundColor(getColor(respTubo)[0]); textViewCorTubo_Prumada.setTextColor(getColor(respTubo)[1]);
        if (respFibra.length() > 0) textViewCorFibra_Prumada.setText(respFibra); textViewCorFibra_Prumada.setBackgroundColor(getColor(respFibra)[0]); textViewCorFibra_Prumada.setTextColor(getColor(respFibra)[1]);

    }

    private Integer[] getColor(String index){

        Object[] coresBackground =
                {
                        "VERDE " + Color.GREEN + " " + Color.BLACK,
                        "AMARELO " + Color.YELLOW + " " + Color.BLACK,
                        "AMARELA " + Color.YELLOW + " " + Color.BLACK,
                        "BRANCO " + Color.WHITE + " " + Color.BLACK,
                        "BRANCA " + Color.WHITE + " " + Color.BLACK,
                        "AZUL " + Color.BLUE + " " + Color.WHITE,
                        "VERMELHO " + Color.RED + " " + Color.WHITE,
                        "VERMELHA " + Color.RED + " " + Color.WHITE,
                        "VIOLETA " + Color.parseColor("#FF8E007D") + " " + Color.WHITE,
                        "ROSA " + Color.parseColor("#FF96B0") + " " + Color.BLACK,
                        "MARROM " + Color.parseColor("#FFA95935") + " " + Color.WHITE,
                };

        for(Object o : coresBackground){

            String[] item = String.valueOf(o).split(" ");

            String nome_cor = item[0];
            Integer cor_fundo = Integer.valueOf(item[1]);
            Integer cor_texto = Integer.valueOf(item[2]);

            if(nome_cor.equals(index)){
                return new Integer[]{cor_fundo, cor_texto};
            }

        }

        return null;
    }

    private void limpar(Integer index){

        int length = textViewCorFibra_Prumada.length()
        +textViewCorTubo_Prumada.length()
        +textViewBandejaDGOI_Prumada.length()
        +textViewCabo_Prumada.length()
        +textViewCaixa_Prumada.length()
        +editTextNumero_Prumada.length();

        if(length > 0) {

            if(index == 1) editTextNumero_Prumada.setText("");

            textViewCabo_Prumada.setText("");
            textViewCorTubo_Prumada.setText("");
            textViewCorFibra_Prumada.setText("");
            textViewBandejaDGOI_Prumada.setText("");
            textViewCaixa_Prumada.setText("");

            textViewCorFibra_Prumada.setBackgroundResource(R.color.cinza_fundo);
            textViewCorTubo_Prumada.setBackgroundResource(R.color.cinza_fundo);

            textViewCorTubo_Prumada.setTextColor(Color.BLACK);
            textViewCorFibra_Prumada.setTextColor(Color.BLACK);

            layout_toast.setBackgroundColor(Color.parseColor("#FF720098"));
            text_toast.setTextColor(Color.WHITE);
            text_toast.setText("LIMPANDO DADOS, AGUARDE...");

        } else {

            layout_toast.setBackgroundColor(Color.RED);
            text_toast.setTextColor(Color.WHITE);
            text_toast.setText("NÃO HÁ DADOS PARA LIMPAR!");

        }

        toast.show();

    }
}