package es.pamp.eltiempo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import modelo.Tiempo;

/**
 * Created by pamp on 02/04/17.
 */

public class TiempoFragmento extends Fragment implements AdapterView.OnItemSelectedListener {
    private Tiempo tiempo;
    private TextView ciudadTV;
    private Spinner ciudades;
    private String ciudad;
    private View v;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tiempolayout,container,false);

        // Inicialización de Spinner ciudades
        ciudades = (Spinner)v.findViewById(R.id.ciudades_spinner);

        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.INVISIBLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(),R.array.ciudades_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ciudades.setAdapter(adapter);

        ciudadTV = (TextView)v.findViewById(R.id.ciudadTV);
        ciudades.setOnItemSelectedListener(this);

        if (tiempo!=null){
            escribreTiempo(tiempo);
        }

        return v;

    }
    public void escribreTiempo (Tiempo tiempo){
        ciudadTV.setText(tiempo.getCiudad());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0) { //Comprueba que no se haya seleccionado el mensaje de: Seleccione localidad
            ciudad = (String) parent.getItemAtPosition(position);
            tiempo = new Tiempo(ciudad);
            //TODO Borrar datos de pantalla
            //progressBar.setVisibility(View.GONE);

            tiempo.pideTiempo(ciudad);

            if (tiempo.getValido()){
                //progressBar.setVisibility(View.INVISIBLE);
                //TODO mensaje de error
            }else{
                //progressBar.setVisibility(View.INVISIBLE);
                escribreTiempo(tiempo);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void solicitudEnviada(){
        progressBar.setVisibility(View.GONE);
        ciudadTV.setText(tiempo.getCiudad());
    }
    public void datosRecibidos(){
        progressBar.setVisibility(View.INVISIBLE);
        ciudadTV.setText(tiempo.getCiudad());
    }
}
