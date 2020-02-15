package demo.pushnotification.weatherapp.network;


import android.app.Application;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import demo.pushnotification.weatherapp.utils.CommonUtils;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;
    Application application;

    ApiModule(){
    }


    @Singleton
    @Provides
    Retrofit provideRetrofit(Application application){
        this.application=application;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(CommonUtils.BASE_URL)
                    .client(provideOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @Singleton
    @Provides
    ApiService provideApiService(){
        return  retrofit.create(ApiService.class);
    }



    @Singleton
    @Provides
    Cache provideOkhhtpCache(){
        int size =10*1000*1000;
        Cache cache=new Cache(application.getCacheDir(),size);
        return cache;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(provideOkhhtpCache());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        okHttpClient = httpClient.build();
        return okHttpClient;
    }

    @Singleton
    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

}
