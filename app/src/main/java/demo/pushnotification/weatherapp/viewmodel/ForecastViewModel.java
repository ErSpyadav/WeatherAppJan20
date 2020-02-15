package demo.pushnotification.weatherapp.viewmodel;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import demo.pushnotification.weatherapp.model.WeatherForecastResult;
import demo.pushnotification.weatherapp.network.ApiService;
import demo.pushnotification.weatherapp.network.MyApplication;
import demo.pushnotification.weatherapp.utils.CommonUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ForecastViewModel extends AndroidViewModel {
    public MutableLiveData<Location> locationMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<WeatherForecastResult> weatherResult=new MutableLiveData<>();

    @Inject
    CompositeDisposable disposable ;

    @Inject
    Retrofit retrofit;

    @Inject
    ApiService apiService;

    private Application application;

    public ForecastViewModel(@NonNull Application application) {
        super(application);
        this.application=application;

    }


    public void updateLocation(Location location){
        locationMutableLiveData.postValue(location);
        fetchForeCast(location);
    }

    public void fetchForeCast(Location location){
        ((MyApplication)application).getApiComponent().inject(this);

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
