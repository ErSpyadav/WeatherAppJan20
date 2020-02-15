package demo.pushnotification.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Coord {

  @SerializedName("lon")
  private double lon;

  @SerializedName("lat")
  private double lat;

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  @NonNull
  @Override
  public String toString() {
    return new StringBuilder("["+lon+","+lat+"]").toString();
  }
}