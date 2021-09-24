package br.ribeiro.suporteavancadofttx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ribeiro.suporteavancadofttx.R;

public class CalcularLateralFusionada extends AppCompatActivity {

    private EditText editTextPDA_LF, editTextprimeiraSecundaria_LF;
    private TextView textViewCorTubo_LF, textViewCorFibra_LF;
    private Button buttonVoltar_LF, buttonCalcular_LF, buttonLimpar_LF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_lateral_fusionada);

        editTextPDA_LF = (EditText) findViewById(R.id.editTextPDA_LF);
        editTextprimeiraSecundaria_LF = (EditText) findViewById(R.id.editTextprimeiraSecundaria_LF);
        textViewCorTubo_LF = (TextView) findViewById(R.id.textViewCorTubo_LF);
        textViewCorFibra_LF = (TextView) findViewById(R.id.textViewCorFibra_LF);
        buttonVoltar_LF = (Button) findViewById(R.id.buttonVoltar_LF);
        buttonCalcular_LF = (Button) findViewById(R.id.buttonCalcular_LF);
        buttonLimpar_LF = (Button) findViewById(R.id.buttonLimpar_LF);

        LayoutInflater inflater = getLayoutInflater();
        View layout_toast = inflater.inflate(R.layout.toast,(ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text_toast = layout_toast.findViewById(R.id.textToast);
        layout_toast.setBackgroundColor(Color.parseColor("#FF720098"));

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setView(layout_toast);

        buttonVoltar_LF.setOnClickListener(v -> {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        });

        buttonLimpar_LF.setOnClickListener(v -> {

            if(editTextPDA_LF.getText().length() > 0 || editTextprimeiraSecundaria_LF.getText().length() > 0)
            {
                editTextPDA_LF.getText().clear();
                editTextprimeiraSecundaria_LF.getText().clear();
                editTextPDA_LF.requestFocus();

                textViewCorFibra_LF.setText("");
                textViewCorFibra_LF.setBackgroundColor(0);

                textViewCorTubo_LF.setText("");
                textViewCorTubo_LF.setBackgroundColor(0);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editTextPDA_LF,InputMethodManager.SHOW_IMPLICIT);

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

        buttonCalcular_LF.setOnClickListener(v -> {
            CalcularLateralFusionada.this.runOnUiThread(() -> {

                try{
                    calcularLateralFusionada();
                } catch (Exception e){
                    layout_toast.setBackgroundColor(Color.RED);
                    text_toast.setTextColor(Color.WHITE);
                    text_toast.setText("DADOS INVÁLIDOS!");
                    toast.show();
                }

            });
        });

    }

    private void calcularLateralFusionada(){

        Integer secPDA = Integer.valueOf(editTextPDA_LF.getText().toString());
        Integer secCaixa = Integer.valueOf(editTextprimeiraSecundaria_LF.getText().toString());

        int indiceFibra;
        int o = secPDA - (secCaixa - 1);
        String respTubo = "", respFibra = "";

        String[] coresTubo = {"", "VERDE","AMARELO"
                ,"BRANCO","AZUL","VERMELHO","VIOLETA"};

        if(o >= 1 && o < 1+6){

            respTubo = coresTubo[1];
            respFibra = coresTubo[o];

        }else if(o >= 7 && o < 7+6){
            respTubo = coresTubo[2];
            indiceFibra = o-6;
            respFibra = coresTubo[indiceFibra];

        }else if(o >= 13 && o < 13+6){
            respTubo = coresTubo[3];
            indiceFibra=o-12;
            respFibra = coresTubo[indiceFibra];

        }else if(o >= 19 && o < 19+6){
            respTubo = coresTubo[4];
            indiceFibra=o-18;
            respFibra = coresTubo[indiceFibra];

        }else if(o >= 25 && o < 25+6){
            respTubo = coresTubo[5];
            indiceFibra=o-24;
            respFibra = coresTubo[indiceFibra];
        }

        textViewCorFibra_LF.setText(respFibra);
        textViewCorTubo_LF.setText(respTubo);

        Integer cor_texto_fibra = 0, cor_fundo_fibra = 0,cor_texto_tubo = 0, cor_fundo_tubo = 0;

        cor_fundo_fibra = getColor(respFibra)[0];
        cor_texto_fibra = getColor(respFibra)[1];

        cor_fundo_tubo = getColor(respTubo)[0];
        cor_texto_tubo = getColor(respTubo)[1];

        textViewCorTubo_LF.setBackgroundColor(cor_fundo_tubo);
        textViewCorTubo_LF.setTextColor(cor_texto_tubo);

        textViewCorFibra_LF.setTextColor(cor_texto_fibra);
        textViewCorFibra_LF.setBackgroundColor(cor_fundo_fibra);

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

    private Integer[] getColor(String index){

        Object[] coresBackground =
            {
                    "VERDE " + Color.GREEN + " " + Color.BLACK,
                    "AMARELO " + Color.YELLOW + " " + Color.BLACK,
                    "BRANCO " + Color.WHITE + " " + Color.BLACK,
                    "AZUL " + Color.BLUE + " " + Color.WHITE,
                    "VERMELHO " + Color.RED + " " + Color.WHITE,
                    "VIOLETA " + Color.parseColor("#FF8E007D") + " " + Color.WHITE
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


}