package example.com.paddelwear;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;

/**
 * Created by usuwi on 08/06/2017.
 */

public class Steps extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CardFragment cardFragment = CardFragment.create("Pasos:", "911 en 13 minutos", R.drawable.foot_steps64);
        fragmentTransaction.add(R.id.container, cardFragment);
        fragmentTransaction.commit();
    }
}
