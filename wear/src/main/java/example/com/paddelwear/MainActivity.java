package example.com.paddelwear;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WearableRecyclerView;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends WearableActivity {

    String[] elements = {"Partida", "Terminar partida", "Historial", "Notificación", "Pasos", "Pulsaciones", "Terminar partida"};

    private static final String WEAR_SEND_TEXT = "/mandar_texto";
    private GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WearableRecyclerView list = (WearableRecyclerView) findViewById(R.id.list);
        MyAdapter myAdapter = new MyAdapter(this, elements);
        myAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer tag = (Integer) v.getTag();
                switch (tag) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, Counter.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, Confirmation.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, MatchHistory.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, Steps.class));
                        break;
                }
            }
        });
        list.setAdapter(myAdapter);
        list.setCenterEdgeItems(true);
        list.setLayoutManager(new MyChildLayoutManager(this));
        list.setCircularScrollingGestureEnabled(true);
        list.setScrollDegreesPerScreen(180);
        list.setBezelWidth(0.5f);


        apiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();

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
}
