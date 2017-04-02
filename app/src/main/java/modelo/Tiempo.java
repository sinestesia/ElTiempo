package modelo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pamp on 02/04/17.
 */

public class Tiempo {
    private String ciudad;
    private String resultadoPeticion;
    private boolean valido;

    public void pideTiempo(String ciudad){
        //devuelve true si se ha realizado con éxito la consulta de la información.

        new RecuperarDatos().execute();//solicita los datos a la web y recupera el String resultadoPeticion

        //Ejemplo petición http://www.androidauthority.com/use-remote-web-api-within-android-app-617869/

        //TODO Cargar valores recuperados en el objeto tiempo

    }


    public class RecuperarDatos  extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            //TODO poner barra de progreso
            //progressBar.setVisibility(View.VISIBLE);
            //responseView.setText("");
            ciudad="peticion solicitada";
        }

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Madrid&appid=db87faa1ae2eb9f5b56d2d2bd6e11ff2&units=metric&lang=es");
                //URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
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
            resultadoPeticion = respuesta;
            if (respuesta == null) {
                valido = false;
            } else {
                valido = true;
            }
            //TODO Poner barra de progreso
            //progressBar.setVisibility(View.GONE);

            ciudad ="Petición satisfactoria";

        }
    }

    public Tiempo(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public boolean getValido() {
        return valido;
    }
}
