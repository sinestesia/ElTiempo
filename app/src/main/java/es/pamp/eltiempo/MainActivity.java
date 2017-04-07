package es.pamp.eltiempo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import modelo.Tiempo;

public class MainActivity extends AppCompatActivity {
    private boolean vertical;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    public Tiempo tiempo;
    public Tiempo tiempo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();



        FrameLayout fl = (FrameLayout)findViewById(R.id.elTiempo2); //Para comprobar si existe

        tiempo = new Tiempo(); //TODO Revisat tiempo no se pasa información de este objeto a tiempofragmento.tiempo
        tiempo2 = new Tiempo();


        if (fl!=null) { //Comprueba si existe el framelayout, si existe la vista es horizontal
            TiempoFragmento tiempoFragmento2 = new TiempoFragmento();
            tiempoFragmento2.setTiempo(tiempo2);
            transaction.replace(R.id.elTiempo2, tiempoFragmento2);
            vertical=false;
        }else{ //TODO Quitar si no es necesario y eliminar variable vertical
            vertical=true;
        }

        TiempoFragmento tiempoFragmento = new TiempoFragmento();

        tiempo.setCiudad("Prueba");
        tiempoFragmento.setTiempo(tiempo);
        transaction.replace(R.id.elTiempo1, tiempoFragmento);
        transaction.commit();
    }
}
