package demo.pushnotification.weatherapp.ui;


import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import demo.pushnotification.weatherapp.R;
import demo.pushnotification.weatherapp.adapter.WeatherForecastAdapter;
import demo.pushnotification.weatherapp.listener.ItemListener;
import demo.pushnotification.weatherapp.model.WeatherForecastResult;
import demo.pushnotification.weatherapp.utils.AppUtil;
import demo.pushnotification.weatherapp.utils.CommonUtils;
import demo.pushnotification.weatherapp.viewmodel.ForecastViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment implements ItemListener {

    @BindView(R.id.recycleview_forecast)
    RecyclerView recyclerView;

    @BindView(R.id.city_name)
    TextView city;

    @BindView(R.id.text_date_time)
    TextView date;

    @BindView(R.id.weather_icon)
    ImageView weatheImg;

    @BindView(R.id.humidity)
    TextView humidity;

    @BindView(R.id.pressure)
    TextView pressure;

    private ForecastViewModel viewModel;

    private String TAG = "ForecastFragment.class";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Location currentLocation;
    private ItemListener listener;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ForeCastActivity.getViewModel();

        viewModel.locationMutableLiveData.observe(this, location -> {
            currentLocation = location;
            Log.d(TAG, "Lat:" + location.getLatitude() + ":" + location.getLongitude());

        });

        viewModel.weatherResult.observe(this, weatherForecastResult -> {
            Log.d(TAG, weatherForecastResult.city.getName());
            dispplayForecastInfo(weatherForecastResult);
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        ButterKnife.bind(this, view);

        listener = this;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.fetchForeCast(currentLocation);
            mSwipeRefreshLayout.setRefreshing(false);

        });

        return view;
    }


    private void dispplayForecastInfo(WeatherForecastResult weatherForecastResult) {
        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult, listener);
        recyclerView.setAdapter(adapter);
        updateUi(weatherForecastResult, 0);
    }

    @SuppressLint("SetTextI18n")
    private void updateUi(WeatherForecastResult weatherForecastResult, int position) {
        city.setText(new StringBuilder(weatherForecastResult.city.getName()));
        date.setText(new StringBuilder(CommonUtils.convertUnixToDate(weatherForecastResult.list.get(position).dt)));
        AppUtil.setWeatherIcon(getContext(), weatheImg, weatherForecastResult.list.get(position).weather.get(0).id);
        humidity.setText("Humidity : " + new StringBuilder(weatherForecastResult.list.get(position).main.humidity));
        pressure.setText("Air: " + new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).wind.getSpeed())).append("m/s"));

    }

    @Override
    public void onItemSelected(WeatherForecastResult result, int position) {
        updateUi(result, position);
    }
}
