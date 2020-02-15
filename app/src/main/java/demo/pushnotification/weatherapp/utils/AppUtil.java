package demo.pushnotification.weatherapp.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.os.ConfigurationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import demo.pushnotification.weatherapp.R;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class AppUtil {
  private static Interpolator fastOutSlowIn;

  /**
   * Get timestamp of start of day 00:00:00
   *
   * @param calendar instance of {@link Calendar}
   * @return timestamp
   */
  public static long getStartOfDayTimestamp(Calendar calendar) {
    Calendar newCalendar = Calendar.getInstance(TimeZone.getDefault());
    newCalendar.setTimeInMillis(calendar.getTimeInMillis());
    newCalendar.set(Calendar.HOUR_OF_DAY, 0);
    newCalendar.set(Calendar.MINUTE, 0);
    newCalendar.set(Calendar.SECOND, 0);
    newCalendar.set(Calendar.MILLISECOND, 0);
    return newCalendar.getTimeInMillis();
  }

  /**
   * Get timestamp of end of day 23:59:59
   *
   * @param calendar instance of {@link Calendar}
   * @return timestamp
   */

  public static long getEndOfDayTimestamp(Calendar calendar) {
    Calendar newCalendar = Calendar.getInstance(TimeZone.getDefault());
    newCalendar.setTimeInMillis(calendar.getTimeInMillis());
    newCalendar.set(Calendar.HOUR_OF_DAY, 23);
    newCalendar.set(Calendar.MINUTE, 59);
    newCalendar.set(Calendar.SECOND, 59);
    newCalendar.set(Calendar.MILLISECOND, 0);
    return newCalendar.getTimeInMillis();
  }


  public static Calendar addDays(Calendar cal, int days) {
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    calendar.setTimeInMillis(cal.getTimeInMillis());
    calendar.add(Calendar.DATE, days);
    return calendar;
  }


  public static void setWeatherIcon(Context context, ImageView imageView, int weatherCode) {
    if (weatherCode / 100 == 2) {
      Glide.with(context).load(R.drawable.ic_storm_weather).into(imageView);
    } else if (weatherCode / 100 == 3) {
      Glide.with(context).load(R.drawable.ic_rainy_weather).into(imageView);
    } else if (weatherCode / 100 == 5) {
      Glide.with(context).load(R.drawable.ic_rainy_weather).into(imageView);
    } else if (weatherCode / 100 == 6) {
      Glide.with(context).load(R.drawable.ic_snow_weather).into(imageView);
    } else if (weatherCode / 100 == 7) {
      Glide.with(context).load(R.drawable.ic_unknown).into(imageView);
    } else if (weatherCode == 800) {
      Glide.with(context).load(R.drawable.ic_clear_day).into(imageView);
    } else if (weatherCode == 801) {
      Glide.with(context).load(R.drawable.ic_few_clouds).into(imageView);
    } else if (weatherCode == 803) {
      Glide.with(context).load(R.drawable.ic_broken_clouds).into(imageView);
    } else if (weatherCode / 100 == 8) {
      Glide.with(context).load(R.drawable.ic_cloudy_weather).into(imageView);
    }
  }


  public static void showFragment(Fragment fragment, FragmentManager fragmentManager, boolean withAnimation) {
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    if (withAnimation) {
      transaction.setCustomAnimations(R.anim.slide_up_anim, R.anim.slide_down_anim);
    } else {
      transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }
    transaction.add(android.R.id.content, fragment).addToBackStack(null).commit();
  }



}
