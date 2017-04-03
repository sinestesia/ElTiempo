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
