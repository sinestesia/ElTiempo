package adaptadores;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

import modelo.Tiempo;

/**
 * Created by pamp on 02/04/17.
 */

public class TiempoAdaptador extends ArrayAdapter<String> {


    public TiempoAdaptador(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
