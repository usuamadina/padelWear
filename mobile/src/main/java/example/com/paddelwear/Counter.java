package example.com.paddelwear;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import example.com.common.DireccionesGestureDetector;
import example.com.common.Partida;

/**
 * Created by usuwi on 11/06/2017.
 */

public class Counter extends Activity implements GoogleApiClient.ConnectionCallbacks,
        MessageApi.MessageListener, DataApi.DataListener {
    private Partida partida;
    private TextView misPuntos, misJuegos, misSets, susPuntos, susJuegos, susSets;
    private Vibrator vibrador;
    private long[] vibrEntrada = {0l, 500};
    private long[] vibrDeshacer = {0l, 500, 500, 500};

    private GoogleApiClient apiClient;

    private static final String WEAR_SCORE = "/puntuacion";
    private static final String KEY_MY_POINTS = "com.example.padel.key.my_points";
    private static final String KEY_MY_GAMES = "com.example.padel.key.my_games";
    private static final String KEY_MY_SETS = "com.example.padel.key.my_sets";
    private static final String KEY_THEIR_POINTS = "com.example.padel.key.their_points";
    private static final String KEY_THEIR_GAMES = "com.example.padel.key.their_games";
    private static final String KEY_THEIR_SETS = "com.example.padel.key.their_sets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);
        partida = new Partida();
        apiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        vibrador = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        misPuntos = (TextView) findViewById(R.id.misPuntos);
        susPuntos = (TextView) findViewById(R.id.susPuntos);
        misJuegos = (TextView) findViewById(R.id.misJuegos);
        susJuegos = (TextView) findViewById(R.id.susJuegos);
        misSets = (TextView) findViewById(R.id.misSets);
        susSets = (TextView) findViewById(R.id.susSets);
        actualizaNumeros();
        View fondo = findViewById(R.id.fondo);
        fondo.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Counter.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onArriba(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    partida.rehacerPunto();
                    vibrador.vibrate(vibrDeshacer, -1);
                    actualizaNumeros();
                    return true;
                }

                @Override
                public boolean onAbajo(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    partida.deshacerPunto();
                    vibrador.vibrate(vibrDeshacer, -1);
                    actualizaNumeros();
                    return true;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent evento) {
                detector.onTouchEvent(evento);
                return true;
            }
        });
        misPuntos.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Counter.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    partida.puntoPara(true);
                    vibrador.vibrate(vibrEntrada, -1);
                    actualizaNumeros();
                    return true;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent evento) {
                detector.onTouchEvent(evento);
                return true;
            }
        });
        susPuntos.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Counter.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    partida.puntoPara(false);
                    vibrador.vibrate(vibrEntrada, -1);
                    actualizaNumeros();
                    return true;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent evento) {
                detector.onTouchEvent(evento);
                return true;
            }
        });
    }

    void actualizaNumeros() {
        Log.d("MobileCounter","ActualizaNumeros");
        misPuntos.setText(partida.getMisPuntos());
        susPuntos.setText(partida.getSusPuntos());
        misJuegos.setText(partida.getMisJuegos());
        susJuegos.setText(partida.getSusJuegos());
        misSets.setText(partida.getMisSets());
        susSets.setText(partida.getSusSets());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MobileCounter","onStart");
        apiClient.connect();
        Wearable.DataApi.addListener(apiClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e("MobileCounter","onConnected");
        Wearable.DataApi.addListener(apiClient, this);
    }

    @Override
    protected void onStop() {
        Wearable.DataApi.removeListener(apiClient, this);
        Log.e("MobileCounter","onStop(), desconectado escuchador");
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.e("MobileCounter","mensajeRecibido");

    }

    @Override
    public void onDataChanged(DataEventBuffer events) {
        DataMap dataMap;
        for (DataEvent event : events) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                Log.e("MobileCounter","Ha habido evento de cambio");
                DataItem item = event.getDataItem();
                dataMap = DataMapItem.fromDataItem(item).getDataMap();
                final int myPoints= dataMap.getInt(KEY_MY_POINTS);
                final int myGames = dataMap.getInt(KEY_MY_GAMES);
                final int mySets = dataMap.getInt(KEY_MY_SETS);
                final int theirPoints = dataMap.getInt(KEY_THEIR_POINTS);
                final int theirGames = dataMap.getInt(KEY_THEIR_GAMES);
                final int theirSets = dataMap.getInt(KEY_THEIR_SETS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.misPuntos)).setText(Integer.toString(myPoints));
                        ((TextView) findViewById(R.id.misJuegos)).setText(Integer.toString(myGames));
                        ((TextView) findViewById(R.id.misSets)).setText(Integer.toString(mySets));
                        ((TextView) findViewById(R.id.susPuntos)).setText(Integer.toString(theirPoints));
                        ((TextView) findViewById(R.id.susSets)).setText(Integer.toString(theirGames));
                        ((TextView) findViewById(R.id.susJuegos)).setText(Integer.toString(theirSets));
                    }
                });
            }
        }
    }
}
