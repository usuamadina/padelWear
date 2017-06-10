package example.com.paddelwear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by usuwi on 08/06/2017.
 */

public class Confirmation extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);
        ImageButton accept = (ImageButton) findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Confirmation.this, Countdown.class), 9);
            }
        });
        ImageButton cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Accion cancelada", Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Accion aceptada", Toast.LENGTH_SHORT).show(); //Guardamos datos de partida finish(); }
        }
    }
}
