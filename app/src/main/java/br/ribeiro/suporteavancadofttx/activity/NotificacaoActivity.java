package br.ribeiro.suporteavancadofttx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import br.ribeiro.suporteavancadofttx.R;

public class NotificacaoActivity extends AppCompatActivity {

    private WebView webNotificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        String url = getIntent().getStringExtra("url");

        webNotificacao = findViewById(R.id.webNotificacao);
        webNotificacao.getSettings().setJavaScriptEnabled(true);
        webNotificacao.loadUrl(url);

    }
}