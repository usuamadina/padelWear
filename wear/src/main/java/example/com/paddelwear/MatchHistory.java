package example.com.paddelwear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;

/**
 * Created by usuwi on 10/06/2017.
 */

public class MatchHistory extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_2d);
        final GridViewPager paginador = (GridViewPager) findViewById(R.id.paginator);
        paginador.setAdapter(new GridPagerAdapter(this, getFragmentManager()));
    }
}