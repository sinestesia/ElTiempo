package es.pamp.eltiempo;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private String resultadoPeticion;
    private String urlPeticion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tiempolayout,container,false);

        // Inicialización de Spinner ciudades
        ciudades = (Spinner)v.findViewById(R.id.ciudades_spinner);

        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(),R.array.ciudades_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ciudades.setAdapter(adapter);

        ciudadTV = (TextView)v.findViewById(R.id.ciudadTV);
        ciudades.setOnItemSelectedListener(this);

        return v;

    }
    public void escribeTiempo (Tiempo tiempo){
        ciudadTV.setText(tiempo.getCiudad());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0) { //Comprueba que no se haya seleccionado el mensaje de: Seleccione localidad
            ciudad = (String) parent.getItemAtPosition(position);
            tiempo = new Tiempo(ciudad);
            //TODO Borrar datos de pantalla
            progressBar.setVisibility(View.VISIBLE);

            new RecuperarDatos().execute();//solicita los datos a la web y recupera el String resultadoPeticion
            //Ejemplo petición http://www.androidauthority.com/use-remote-web-api-within-android-app-617869/


        }
    }

    public class RecuperarDatos  extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            resultadoPeticion=null;
            borrarInfo();
            progressBar.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(Void... urls) {

            try {
                //construirURL

                urlPeticion=getResources().getString(R.string.direccion_api)+ ciudad +"&appid=" +  getResources().getString(R.string.id_api) + "&units=metric&lang=es";
                URL url = new URL(urlPeticion);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String respuesta) {
            progressBar.setVisibility(View.GONE);
            resultadoPeticion = respuesta;
            if (respuesta == null) {
                //TODO mostrarerror
            } else {
                mostrarInfo();
            }
        }
    }
    public void borrarInfo(){
        ciudadTV.setVisibility(View.INVISIBLE);
        //TODO Borrar info
    }
    public void mostrarInfo(){
        ciudadTV.setVisibility(View.VISIBLE);
        ciudadTV.setText(resultadoPeticion);
        //TODO Mostrar info
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
