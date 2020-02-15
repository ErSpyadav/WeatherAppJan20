package demo.pushnotification.weatherapp.network;

import android.app.Application;

public class MyApplication extends Application {

    ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        apiComponent =DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApiComponent getApiComponent(){
        return apiComponent;
    }

}
