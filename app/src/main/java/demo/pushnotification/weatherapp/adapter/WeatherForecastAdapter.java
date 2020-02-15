package demo.pushnotification.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import demo.pushnotification.weatherapp.R;
import demo.pushnotification.weatherapp.listener.ItemListener;
import demo.pushnotification.weatherapp.model.WeatherForecastResult;
import demo.pushnotification.weatherapp.utils.AppUtil;
import demo.pushnotification.weatherapp.utils.CommonUtils;


public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    private Context context;
    private WeatherForecastResult weatherForecastResult;
    private int selectedIndex = 0;
    private ItemListener listener;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult, ItemListener listener) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_date_time)TextView date_time;
        @BindView(R.id.weather_temp) TextView temperature;
        @BindView(R.id.weather_desc) TextView desc;
        @BindView(R.id.weather_icon)ImageView img_weather;

        @BindView(R.id.main_Container) LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
          ButterKnife.bind(this,itemView);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast, parent, false);
        return new MyViewHolder(rootView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppUtil.setWeatherIcon(context, holder.img_weather, weatherForecastResult.list.get(position).weather.get(0).id);
        holder.date_time.setText(new StringBuilder(CommonUtils.convertUnixToDate(weatherForecastResult.list.get(position).dt)));
        holder.desc.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).description));
        holder.temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.temp)).append(" \u2103"));
        holder.mainLayout.setOnClickListener(view -> {
            selectedIndex = position;
            notifyDataSetChanged();
            listener.onItemSelected(weatherForecastResult, position);
        });
        if (selectedIndex == position) {
            holder.mainLayout.setBackgroundResource(R.drawable.rectangle_background_selected);
        } else {
            holder.mainLayout.setBackgroundResource(R.drawable.rectangle_background);
        }
    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

}
