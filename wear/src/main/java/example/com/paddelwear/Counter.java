package example.com.paddelwear;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Date;

import example.com.common.DireccionesGestureDetector;
import example.com.common.Partida;

/**
 * Created by usuwi on 10/06/2017.
 */

public class Counter extends WearableActivity {
    private Partida match;
    private TextView myPoints, myGames, mySets, theirPoints, theirGames, theirSets, time;
    private Vibrator vibrator;
    private long[] vibrEntrada = {0l, 500};
    private long[] vibrDeshacer = {0l, 500, 500, 500};
    private DismissOverlayView dismissOverlay;

    private Typeface normalFont = Typeface.create("sans-serif", 0);
    private Typeface thinFont = Typeface.create
            ("sans-serif-thin", 0);
    private Calendar c;

    private static final String START_MOBILE_ACTIVITY="/arrancar_actividad";
    private GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);
        setAmbientEnabled();
        dismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        dismissOverlay.setIntroText("Para salir de la aplicación, haz una pulsación larga");
        dismissOverlay.showIntroIfNecessary();

        match = new Partida();

        time = (TextView) findViewById(R.id.time);
        c = Calendar.getInstance();
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        myPoints = (TextView) findViewById(R.id.myPoints);
        myPoints.setTextColor(Color.GREEN);
        theirPoints = (TextView) findViewById(R.id.theirPoints);
        theirPoints.setTextColor(Color.GREEN);
        myGames = (TextView) findViewById(R.id.myGames);
        myGames.setTextColor(Color.GREEN);
        theirGames = (TextView) findViewById(R.id.theirGames);
        theirGames.setTextColor(Color.GREEN);
        mySets = (TextView) findViewById(R.id.mySets);
        mySets.setTextColor(Color.GREEN);
        theirSets = (TextView) findViewById(R.id.theirSets);
        theirSets.setTextColor(Color.GREEN);
        actualizaNumeros();
        View fondo = findViewById(R.id.background);

        apiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        sendMessage(START_MOBILE_ACTIVITY, "Arrancar actividad en el móvil");

        // Configuramos el detector de pulsaciones


        fondo.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Counter.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onArriba(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    match.rehacerPunto();
                    vibrator.vibrate(vibrDeshacer, -1);
                    actualizaNumeros();
                    return true;
                }

                @Override
                public boolean onAbajo(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    match.deshacerPunto();
                    vibrator.vibrate(vibrDeshacer, -1);
                    actualizaNumeros();
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    dismissOverlay.show();
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent evento) {
                detector.onTouchEvent(evento);
                return true;
            }
        });
        myPoints.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Counter.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    match.puntoPara(true);
                    vibrator.vibrate(vibrEntrada, -1);
                    actualizaNumeros();
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    dismissOverlay.show();
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent evento) {
                detector.onTouchEvent(evento);
                return true;
            }
        });
        theirPoints.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Counter.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    match.puntoPara(false);
                    vibrator.vibrate(vibrEntrada, -1);
                    actualizaNumeros();
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    dismissOverlay.show();
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
        myPoints.setText(match.getMisPuntos());
        theirPoints.setText(match.getSusPuntos());
        myGames.setText(match.getMisJuegos());
        theirGames.setText(match.getSusJuegos());
        mySets.setText(match.getMisSets());
        theirSets.setText(match.getSusSets());
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        myPoints.setTypeface(thinFont);
        myPoints.setTextColor(Color.WHITE);
        myPoints.getPaint().setAntiAlias(false);
        mySets.setTypeface(thinFont);
        mySets.getPaint().setAntiAlias(false);
        mySets.setTextColor(Color.WHITE);
        myGames.setTypeface(thinFont);
        myGames.setTextColor(Color.WHITE);
        myGames.getPaint().setAntiAlias(false);


        theirPoints.setTypeface(thinFont);
        theirPoints.setTextColor(Color.WHITE);
        theirPoints.getPaint().setAntiAlias(false);
        theirSets.setTypeface(thinFont);
        theirSets.setTextColor(Color.WHITE);
        theirSets.getPaint().setAntiAlias(false);
        theirGames.setTypeface(thinFont);
        theirGames.setTextColor(Color.WHITE);
        theirGames.getPaint().setAntiAlias(false);


        setHour();
        time.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
        myPoints.setTypeface(normalFont);
        myPoints.setTextColor(Color.GREEN);
        myPoints.getPaint().setAntiAlias(true);
        mySets.setTypeface(normalFont);
        mySets.setTextColor(Color.GREEN);
        mySets.getPaint().setAntiAlias(true);
        myGames.setTypeface(normalFont);
        myGames.setTextColor(Color.GREEN);
        myGames.getPaint().setAntiAlias(true);

        theirPoints.setTypeface(normalFont);
        theirPoints.setTextColor(Color.GREEN);
        theirPoints.getPaint().setAntiAlias(true);
        theirSets.setTypeface(normalFont);
        theirSets.setTextColor(Color.GREEN);
        theirSets.getPaint().setAntiAlias(true);
        theirGames.setTypeface(normalFont);
        theirGames.setTextColor(Color.GREEN);
        theirGames.getPaint().setAntiAlias(true);
        time.setVisibility(View.GONE);

    }

    public void setHour(){
        c.setTime(new Date());
        time.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        Log.e("Counter","setHour : " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
    }

    @Override
    public void onUpdateAmbient() {
        Log.e("Counter", "onUpdateAmbient");
        setHour();
        super.onUpdateAmbient();


    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onStop() {
        if (apiClient != null && apiClient.isConnected()) {
            apiClient.disconnect();
        }
        super.onStop();
    }

    private void sendMessage(final String path, final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(apiClient).await();
                for (Node nodo : nodes.getNodes()) {
                    Wearable.MessageApi.sendMessage(apiClient, nodo.getId(), path, text.getBytes()).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult resultado) {
                            if (!resultado.getStatus().isSuccess()) {
                                Log.e("sincronizacion", "Error al mandar mensaje. Código:" + resultado.getStatus().getStatusCode());
                            }
                        }
                    });
                }
            }
        }).start();
    }
}