package example.com.paddelwear;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

/**
 * Created by usuwi on 10/06/2017.
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {
    private final Context contexto;
    private final String t[][] = {{"21/5/15", "juego 1", "juego 1", "juego 2", "juego 2"}, {"23/5/15", "juego 1", "juego 1"}};
    private final String c[][] = {{"6-2 6-7", "6-2, 65% puntos ganados, 81% al servicio", "22 min. 623 pasos, 33º", "6-7, 48% puntos ganados, 64% al servicio", "36 min. 923 pasos, 32º"}, {"4-6", "4-6, 45% puntos ganados, 53% al servicio", "28 min. 723 pasos, 28º"}};

    public GridPagerAdapter(Context contexto, FragmentManager fm) {
        super(fm);
        this.contexto = contexto;
    }

    @Override
    public android.app.Fragment getFragment(int fila, int col) {
        int icono = R.drawable.foot_steps;
        if ((col == 0) || ((col - 1) % 2 == 0)) {
            if ((c[fila][col].charAt(0) >= c[fila][col].charAt(2))) {
                icono = R.drawable.happy_andy;
            } else {
                icono = R.drawable.sad_andy;
            }
        }
        CardFragment fragment = CardFragment.create(t[fila][col], c[fila][col], icono);
        return fragment;
    }


    @Override
    public int getRowCount() {
        return t.length;
    }

    @Override
    public int getColumnCount(int row) {
        return t[row].length;
    }

    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        return contexto.getResources().getDrawable(R.drawable.padel2);
    }
}