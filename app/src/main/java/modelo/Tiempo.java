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
    private String temperatura;
    private int posicion;



    public Tiempo() {
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public void setResultadoPeticion(String resultadoPeticion) {
        this.resultadoPeticion = resultadoPeticion;
    }
    public String getResultadoPeticion() {
        return resultadoPeticion;
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
}
