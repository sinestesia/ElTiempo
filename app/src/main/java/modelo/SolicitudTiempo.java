package modelo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SolicitudTiempo {

    @SerializedName("coord")
    @Expose
    private Coord coord;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;

    @SerializedName("main")
    @Expose
    private Main main;


    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }
    public Main getMain() {
        return main;
    }
}
