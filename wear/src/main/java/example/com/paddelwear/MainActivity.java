package example.com.paddelwear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableRecyclerView;
import android.view.View;

public class MainActivity extends Activity {

    String[] elements = {"Partida", "Terminar partida", "Historial", "Notificación", "Pasos", "Pulsaciones", "Terminar partida"};

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
    }
}
