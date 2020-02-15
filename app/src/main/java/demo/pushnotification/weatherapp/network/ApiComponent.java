package demo.pushnotification.weatherapp.network;


import javax.inject.Singleton;

import dagger.Component;
import demo.pushnotification.weatherapp.viewmodel.ForecastViewModel;

@Singleton
@Component(modules = {ApplicationModule.class,ApiModule.class})
public interface ApiComponent {
    void inject(ForecastViewModel forecastViewModel);
}
