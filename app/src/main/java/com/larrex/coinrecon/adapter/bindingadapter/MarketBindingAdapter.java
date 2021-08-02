package com.larrex.coinrecon.adapter.bindingadapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.type.DateTime;
import com.larrex.coinrecon.R;

import org.joda.time.DateTimeZone;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class MarketBindingAdapter {

    @BindingAdapter("setPrice")
    public static void setPrice(TextView textView, double price) {

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(8);

//        DecimalFormat decimalFormat = new DecimalFormat("#.#######");
//        decimalFormat.setRoundingMode(RoundingMode.CEILING);

        textView.setText(formatter.format(price));
    }

    @BindingAdapter("set24hPriceChanged")
    public static void set24hPriceChanged(TextView textView, double changed) {

        if (changed > 0) {
            textView.setTextColor(Color.parseColor("#72E672"));
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setRoundingMode(RoundingMode.UP);
            String volume = decimalFormat.format(changed);
            textView.setText("▲ " + volume + "%");
        } else {
            textView.setTextColor(Color.parseColor("#F33333"));
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setRoundingMode(RoundingMode.UP);
            String volume = decimalFormat.format(changed);
            textView.setText("▼ " + volume + "%");
        }

    }

    @BindingAdapter("set24hMarketCapChanged")
    public static void set24hMarketCapChanged(TextView textView, double changed) {

        if (changed > 0) {
            textView.setTextColor(Color.parseColor("#72E672"));
            DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
            decimalFormat.setRoundingMode(RoundingMode.UP);
            String volume = decimalFormat.format(changed);
            textView.setText(volume + "%");
        } else {
            textView.setTextColor(Color.parseColor("#F33333"));
            DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
            decimalFormat.setRoundingMode(RoundingMode.UP);
            String volume = decimalFormat.format(changed);
            textView.setText(volume + "%");
        }

    }

    @BindingAdapter("formatDate")
    public static void formatDate(TextView textView, String time) {

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.ENGLISH);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(time);
            String formattedDate = outputFormat.format(date);
            textView.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @BindingAdapter("setSupply")
    public static void setSupply(TextView textView, double changed) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        String volume = decimalFormat.format(changed);
        textView.setText(volume);
    }

    @BindingAdapter("setCoinName")
    public static void setCoinName(TextView textView, String name) {
        textView.setText(name);
    }

    @BindingAdapter("setCoinSymbol")
    public static void setCoinSymbol(TextView textView, String symbol) {
        textView.setText(symbol);
    }

    @BindingAdapter("setCoinImage")
    public static void setCoinImage(ImageView imageView, String url) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.color.light_gray);
        requestOptions.placeholder(R.color.light_gray);

        Glide.with(imageView).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);

    }
}
