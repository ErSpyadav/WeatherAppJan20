package demo.pushnotification.weatherapp.listener;

import demo.pushnotification.weatherapp.model.WeatherForecastResult;
import demo.pushnotification.weatherapp.model.WeatherResult;

public interface ItemListener {
    void onItemSelected(WeatherForecastResult result, int position);
}
