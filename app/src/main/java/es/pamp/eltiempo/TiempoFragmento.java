package es.pamp.eltiempo;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import modelo.SolicitudTiempo;

/**
 * Created by pamp on 02/04/17.
 */

public class TiempoFragmento extends Fragment implements AdapterView.OnItemSelectedListener {
    private SolicitudTiempo solicitudTiempo;
    private TextView descripcionTV;
    private ConstraintLayout temperaturasCL;
    private TextView temperaturaTV;
    private TextView temperaturaMinTV;
    private TextView temperaturaMaxTV;
    private TextView velocidadTV;
    private ImageView iconIV;
    private Spinner ciudades;
    private String ciudad;
    private View v;
    private ProgressBar progressBar;
    private String resultadoPeticion;
    private String urlPeticion;
    private int posicion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tiempolayout,container,false);

        // Inicialización de Spinner ciudades
        ciudades = (Spinner)v.findViewById(R.id.ciudades_spinner);

        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.ciudades_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ciudades.setAdapter(adapter);
        iconIV = (ImageView)v.findViewById(R.id.iconIV);
        descripcionTV= (TextView)v.findViewById(R.id.descripcionTV);
        temperaturasCL = (ConstraintLayout)v.findViewById(R.id.temperaturasCL);
        temperaturaTV = (TextView)v.findViewById(R.id.temperaturaTV);
        temperaturaMinTV = (TextView)v.findViewById(R.id.temperaturaMinTV);
        temperaturaMaxTV = (TextView)v.findViewById(R.id.temperaturaMaxTV);
        velocidadTV = (TextView)v.findViewById(R.id.velocidadTV);

        ciudades.setOnItemSelectedListener(this);

        if (resultadoPeticion!=null){
            guardarInfo();
            mostrarInfo();
        }else{
            borrarInfo();
        }

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0) { //Comprueba que no se haya seleccionado el mensaje de: Seleccione localidad
            ciudad = (String) parent.getItemAtPosition(position);
            posicion= position;
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

                urlPeticion=getResources().getString(R.string.direccion_api)+ ciudad +",sp&appid=" +  getResources().getString(R.string.id_api) + "&units=metric&lang=es";
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
                guardarInfo();
                mostrarInfo();
            }
        }
    }

    public void guardarInfo(){

        //GENERADOR de objetos Java de JSON http://www.jsonschema2pojo.org/
        //EJEMPLO RESPUESTA: "{\"coord\":{\"lon\":-83.56,\"lat\":41.66},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"cielo claro\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":9.92,\"pressure\":1019,\"humidity\":61,\"temp_min\":7,\"temp_max\":13},\"visibility\":16093,\"wind\":{\"speed\":1.5,\"deg\":330},\"clouds\":{\"all\":1},\"dt\":1492419120,\"sys\":{\"type\":1,\"id\":2195,\"message\":0.2071,\"country\":\"US\",\"sunrise\":1492426208,\"sunset\":1492474670},\"id\":5174035,\"name\":\"Toledo\",\"cod\":200}\n"

        Gson gson = new Gson();
        solicitudTiempo = gson.fromJson(resultadoPeticion, SolicitudTiempo.class);
    }

    public void borrarInfo(){
        temperaturasCL.setVisibility(View.INVISIBLE);
        iconIV.setVisibility(View.INVISIBLE);
        descripcionTV.setVisibility(View.INVISIBLE);
    }

    public void mostrarInfo(){
        ciudades.setSelection(posicion);
        temperaturasCL.setVisibility(View.VISIBLE);
        iconIV.setVisibility(View.VISIBLE);
        descripcionTV.setVisibility(View.VISIBLE);

        DecimalFormat formatoDecimal = new DecimalFormat("#.#");

        int codigo = (solicitudTiempo.getWeather().get(0).getId());
        switch (codigo/100) {
            case 8:
                if (codigo==800){
                    iconIV.setImageResource(R.drawable.i800);
                }else{
                    iconIV.setImageResource(R.drawable.i8xx);
                }
                break;
            case 7:
                iconIV.setImageResource(R.drawable.i7xx);
                break;
            case 6:
                iconIV.setImageResource(R.drawable.i6xx);
                break;
            case 5:
                iconIV.setImageResource(R.drawable.i5xx);
                break;
            case 3:
                iconIV.setImageResource(R.drawable.i3xx);
                break;
            case 2:
                iconIV.setImageResource(R.drawable.i2xx);
                break;
            default:
                iconIV.setImageResource(R.drawable.i800);
                break;
        }
        descripcionTV.setText(solicitudTiempo.getWeather().get(0).getDescription());
        temperaturaTV.setText(formatoDecimal.format(solicitudTiempo.getMain().getTemp())+"º");
        temperaturaMinTV.setText(formatoDecimal.format(solicitudTiempo.getMain().getTempMin())+"º");
        temperaturaMaxTV.setText(formatoDecimal.format(solicitudTiempo.getMain().getTempMax())+"º");
        velocidadTV.setText(formatoDecimal.format(solicitudTiempo.getWind().getSpeed()) + " km/h");


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getResultadoPeticion() {
        return resultadoPeticion;
    }

    public void setResultadoPeticion(String resultadoPeticion) {
        this.resultadoPeticion = resultadoPeticion;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
