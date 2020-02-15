package demo.pushnotification.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import demo.pushnotification.weatherapp.R;
import demo.pushnotification.weatherapp.model.WeatherForecastResult;
import demo.pushnotification.weatherapp.utils.AppUtil;
import demo.pushnotification.weatherapp.utils.CommonUtils;


public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    private Context context;
    private WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_time, temperature, desc;
        ImageView img_weather;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date_time = itemView.findViewById(R.id.text_date_time);
            temperature = itemView.findViewById(R.id.weather_temp);
            desc = itemView.findViewById(R.id.weather_desc);
            img_weather = itemView.findViewById(R.id.weather_icon);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppUtil.setWeatherIcon(context, holder.img_weather, weatherForecastResult.list.get(position).weather.get(0).id);
        holder.date_time.setText(new StringBuilder(CommonUtils.convertUnixToDate(weatherForecastResult.list.get(position).dt)));
        holder.desc.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).description));
        holder.temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.temp)).append(" \u2103"));

    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

}
