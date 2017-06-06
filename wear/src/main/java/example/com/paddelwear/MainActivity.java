package example.com.paddelwear;
import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WearableRecyclerView;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends Activity {
// Elementos a mostrar en la lista

    String[] elements = {"Partida", "Terminar partida", "Historial", "Notificación", "Pasos", "Pulsaciones", "Terminar partida"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WearableRecyclerView lista = (WearableRecyclerView) findViewById(R.id.list);
        MyAdapter myAdapter = new MyAdapter(this, elements);
        myAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer tag = (Integer) v.getTag();
                Toast.makeText(MainActivity.this, "Elegida opción:" + tag, Toast.LENGTH_SHORT).show();
            }
        });
        lista.setAdapter(myAdapter);
    }

}
