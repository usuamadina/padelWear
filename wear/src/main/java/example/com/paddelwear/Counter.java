package example.com.paddelwear;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import example.com.common.DireccionesGestureDetector;
import example.com.common.Partida;

/**
 * Created by usuwi on 10/06/2017.
 */

public class Counter extends Activity {
    private Partida match;
    private TextView myPoints, myGames, mySets, theirPoints, theirGames, theirSets;
    private Vibrator vibrator;
    private long[] vibrEntrada = {0l, 500};
    private long[] vibrDeshacer = {0l, 500, 500, 500};
    private DismissOverlayView dismissOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);
        dismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        dismissOverlay.setIntroText("Para salir de la aplicación, haz una pulsación larga");
        dismissOverlay.showIntroIfNecessary();

        match = new Partida();
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        myPoints = (TextView) findViewById(R.id.myPoints);
        theirPoints = (TextView) findViewById(R.id.theirPoints);
        myGames = (TextView) findViewById(R.id.myGames);
        theirGames = (TextView) findViewById(R.id.theirGames);
        mySets = (TextView) findViewById(R.id.mySets);
        theirSets = (TextView) findViewById(R.id.theirSets);
        actualizaNumeros();
        View fondo = findViewById(R.id.background);

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
}