package es.pamp.eltiempo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tiempolayout,container,false);

        // Inicialización de Spinner ciudades
        ciudades = (Spinner)v.findViewById(R.id.ciudades_spinner);
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
    public void pideTiempo(String ciudad){
        //TODO Hacer petición  http://api.openweathermap.org/data/2.5/weather?q=Madrid&appid=db87faa1ae2eb9f5b56d2d2bd6e11ff2&units=metric&lang=es
    }
    public void escribreTiempo (Tiempo tiempo){
        ciudadTV.setText(tiempo.getCiudad());
    }
    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0) { //Comprueba que no se haya seleccionado el mensaje de: Seleccione localidad
            ciudad = (String) parent.getItemAtPosition(position);
            tiempo = new Tiempo(ciudad);
            pideTiempo(ciudad);
            escribreTiempo(tiempo);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
