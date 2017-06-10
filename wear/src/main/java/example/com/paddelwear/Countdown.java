package example.com.paddelwear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;

/**
 * Created by usuwi on 10/06/2017.
 */


public class Countdown extends Activity implements DelayedConfirmationView.DelayedConfirmationListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);
        DelayedConfirmationView countdown = (DelayedConfirmationView) findViewById(R.id.countdown);
        countdown.setListener(this);
        countdown.setTotalTimeMs(2000);
        countdown.start();
    }

    @Override
    public void onTimerFinished(View view) {
        Intent i = getIntent();
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onTimerSelected(View view) {
        Intent i = getIntent();
        setResult(RESULT_CANCELED, i);
        finish();
    }
}
