package com.larrex.coinrecon.adapter.bindingadapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.larrex.coinrecon.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsBindingAdapter {

    @BindingAdapter("setNewsImage")
    public static void setNewsImage(ImageView imageView, String url) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.color.light_gray);
        requestOptions.placeholder(R.color.light_gray);

        Glide.with(imageView).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);

    }

    @BindingAdapter("setPrettyTime")
    public static void setPrettyTime(TextView time, String timeString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

        try {
            Date date = dateFormat.parse(timeString);
            PrettyTime prettyTime = new PrettyTime();
            time.setText("• "+prettyTime.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
