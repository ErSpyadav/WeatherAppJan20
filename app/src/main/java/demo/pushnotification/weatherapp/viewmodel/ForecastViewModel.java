package demo.pushnotification.weatherapp.viewmodel;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import demo.pushnotification.weatherapp.model.WeatherForecastResult;
import demo.pushnotification.weatherapp.network.ApiService;
import demo.pushnotification.weatherapp.network.RetrofitClient;
import demo.pushnotification.weatherapp.utils.CommonUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ForecastViewModel extends ViewModel {
    public MutableLiveData<Location> locationMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<WeatherForecastResult> weatherResult=new MutableLiveData<>();
    private Retrofit retrofit;
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public void updateLocation(Location location){
        locationMutableLiveData.postValue(location);
        fetchForeCast(location);
    }

    public void fetchForeCast(Location location){
        if(retrofit==null)retrofit = RetrofitClient.getClient();
        if(apiService==null)apiService = retrofit.create(ApiService.class);

            disposable.add(apiService.getForeCastWeatherByLatlng(
                    String.valueOf(location.getLatitude()),
                    String.valueOf(location.getLongitude()),
                    CommonUtils.API_ID,
                    CommonUtils.UNITS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherForecastResult -> {
                        Log.d("ForeCastActivity Res:",weatherForecastResult.list.toString());
                        weatherResult.postValue(weatherForecastResult);
                    }, throwable -> {

                    })
            );

        }

}
