package br.ribeiro.suporteavancadofttx.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import br.ribeiro.suporteavancadofttx.R;
import br.ribeiro.suporteavancadofttx.message.ReceberNotificacao;

public class MainActivity extends AppCompatActivity {

    private Button buttonDgoic, buttonLateralFusionada, buttonPrumada;
    private TextView textContato;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = getIntent().getStringExtra("url");

        if(url != null){
            Intent i = new Intent(this,NotificacaoActivity.class);
            i.putExtra("url",url);
            startActivity(i);
            finish();
        }

        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    FirebaseUser user = task.getResult().getUser();

                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            String token = task.getResult();

                            FirebaseDatabase data = FirebaseDatabase.getInstance();
                            DatabaseReference dataBase = data.getReference();
                            dataBase.child("users").child(user.getUid()).child("token").setValue(token);
                        }
                    });

                }
            }
        });

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        buttonDgoic = findViewById(R.id.buttonCalcDGOIC);
        buttonLateralFusionada = findViewById(R.id.buttonLateralFusionada);
        buttonPrumada = findViewById(R.id.buttonCalcPrumada);
        textContato = findViewById(R.id.textContato);

        textContato.setOnClickListener(v->{
            Intent i = new Intent(this,ContatoActivity.class);
            startActivity(i);
            finish();
        });

        buttonDgoic.setOnClickListener(v -> {
            Intent i = new Intent(this,CalculadoraDgoic.class);
            startActivity(i);
            finish();
        });

        buttonPrumada.setOnClickListener(v -> {
            Intent i = new Intent(this,CalcularPrumada.class);
            startActivity(i);
            finish();
        });

        buttonLateralFusionada.setOnClickListener(v -> {
            Intent i = new Intent(this,CalcularLateralFusionada.class);
            startActivity(i);
            finish();
        });
    }

}