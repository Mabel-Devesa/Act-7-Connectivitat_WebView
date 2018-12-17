package com.mdevesa.activitats.act_7_comunicacions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ReceptorXarxa receptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenim un gestor de les connexions de xarxa
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Obtenim l'estat de la xarxa
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //Si està connectat
        if (networkInfo != null && networkInfo.isConnected()) {
            //Xarxa OK
            Toast.makeText(this,"Xarxa ok", Toast.LENGTH_LONG).show();
        } else {
            //Xarxa no disponible
            Toast.makeText(this,"Xarxa no disponible", Toast.LENGTH_LONG).show();
        }

        //Obtenim l'estat de la xarxa mòbil
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean connectat3G = networkInfo.isConnected();

        //Obtenim l'estat de la xarxa Wi-Fi
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean connectatWifi = networkInfo.isConnected();

        //Instanciar una instancia de la classe receptora i registrar-la amb un filtre d'intent
        //per escoltar unicament els missatges de broadcast sobre la connectivitat del dispositiu
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receptor = new ReceptorXarxa();
        this.registerReceiver(receptor, filter);

    }

    public void onDestroy() {
        super.onDestroy();
        //Donem de baixa el receptor de broadcast quan es destrueix l'aplicació
        if (receptor != null) {
            this.unregisterReceiver(receptor);
        }
    }

    public class ReceptorXarxa extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            //Actualitzar l'estat de la xarxa
            ActualitzaEstatXarxa();
        }
    }
}





