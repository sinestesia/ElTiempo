package es.pamp.eltiempo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private boolean vertical;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    public TiempoFragmento tiempoFragmento;
    public TiempoFragmento tiempoFragmento2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();



        FrameLayout fl = (FrameLayout)findViewById(R.id.elTiempo2); //Para comprobar si existe


        if (fl!=null) { //Comprueba si existe el framelayout, si existe la vista es horizontal
            tiempoFragmento2 = new TiempoFragmento();
            transaction.replace(R.id.elTiempo2, tiempoFragmento2);
            vertical=false;
        }

        tiempoFragmento = new TiempoFragmento();


        if (savedInstanceState!=null){
            tiempoFragmento.setPosicion(savedInstanceState.getInt("POSICION"));
            tiempoFragmento.setCiudad(savedInstanceState.getString("CIUDAD"));
            tiempoFragmento.setResultadoPeticion(savedInstanceState.getString("RESULTADOPETICION"));
        }

        transaction.replace(R.id.elTiempo1, tiempoFragmento);
        transaction.commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CIUDAD", tiempoFragmento.getCiudad());
        outState.putString("RESULTADOPETICION", tiempoFragmento.getResultadoPeticion());
        outState.putInt("POSICION", tiempoFragmento.getPosicion());
    }
}
