package es.pamp.eltiempo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import modelo.Tiempo;

/**
 * Created by pamp on 02/04/17.
 */

public class TiempoFragmento extends Fragment {
    Tiempo tiempo;
    TextView ciudadTV;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tiempolayout,container,false);
        tiempo = new Tiempo("Madrid");//TODO Borrar, ejemplo de inicialización
        ciudadTV = (TextView)v.findViewById(R.id.ciudadTV);

        if (tiempo!=null){
            escribreTiempo(tiempo);
        }

        return v;

    }
    public void escribreTiempo (Tiempo tiempo){
        ciudadTV.setText(tiempo.getCiudad());
    }
    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;
    }

}
