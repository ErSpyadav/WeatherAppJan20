package demo.pushnotification.weatherapp.utils;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    public static String API_ID="197dae68819dff139385948be4d82762";
    public static String BASE_URL="https://api.openweathermap.org/data/2.5/";
    public static final String UNITS = "metric";
    public static Location location=null;

    public static String convertUnixToDate(long dt){
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd EEE MM YYYY");
        String formatted =sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt){
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String formatted =sdf.format(date);
        return formatted;
    }

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        CommonUtils.location = location;
    }
}
