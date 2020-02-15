package demo.pushnotification.weatherapp.network;
import io.reactivex.Observable;
import io.reactivex.Observer;
import demo.pushnotification.weatherapp.model.WeatherForecastResult;
import demo.pushnotification.weatherapp.model.WeatherResult;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("weather")
    Observable<WeatherResult> getWeatherByLatlng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appId,
                                                 @Query("units") String units);

    @GET("forecast")
    Observable<WeatherForecastResult> getForeCastWeatherByLatlng(@Query("lat") String lat,
                                                                 @Query("lon") String lng,
                                                                 @Query("appid") String appId,
                                                                 @Query("units") String units);



}
