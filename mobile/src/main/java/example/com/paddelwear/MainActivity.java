package example.com.paddelwear;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends AppCompatActivity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks,
        DataApi.DataListener{
    private static final String SEND_TEXT_MOBILE = "/mandar_texto";
    private GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.counter_action) {
            startActivity(new Intent(this, Counter.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onStop() {
        Wearable.MessageApi.removeListener(apiClient, this);
        if (apiClient != null && apiClient.isConnected()) {
            apiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener(apiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equalsIgnoreCase(SEND_TEXT_MOBILE)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("Mobile", "onMessageReceived");
                   // textView.setText(textView.getText() + "\n" + new String(mensaje.getData()));
                }
            });
        }

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Log.e("MobileMain","ha cambiado un dato");
    }



    /*  @Override
    public void onMessageReceived(MessageEvent messageEvent) {
*//*
        if (messageEvent.getPath().equalsIgnoreCase(WEAR_SEND_TEXT)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(textView.getText() + "\n" + new String(mensaje.getData()));
                }
            });
        }
*//*

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.MessageApi.addListener(apiClient, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }*/
}
