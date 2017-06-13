package example.com.paddelwear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by usuwi on 13/06/2017.
 */

public class Players extends Activity {
    private static final int SPEECH_REQUEST_CODE = 123;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);
        View.OnClickListener onClickName = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView = (TextView) v;
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            }
        };
        findViewById(R.id.partner).setOnClickListener(onClickName);
        findViewById(R.id.rival_1).setOnClickListener(onClickName);
        findViewById(R.id.rival_2).setOnClickListener(onClickName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datos) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = datos.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String recognizedText = results.get(0);
            textView.setText(recognizedText);
        }
        super.onActivityResult(requestCode, resultCode, datos);
    }
}

