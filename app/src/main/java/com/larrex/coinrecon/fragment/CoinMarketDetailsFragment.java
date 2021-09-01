package com.larrex.coinrecon.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.FragmentCoinMarketDetailsBinding;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.viewmodel.LoginViewModel;
import com.yabu.livechart.model.DataPoint;
import com.yabu.livechart.model.Dataset;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.larrex.coinrecon.di.module.network.RetrofitModule.getChartData;


public class CoinMarketDetailsFragment extends BottomSheetDialogFragment {

    FragmentCoinMarketDetailsBinding binding;

    LoginViewModel viewModel;
    Dataset dataset;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_market_details, container, false);

        Market market = (Market) getArguments().getSerializable("coinData");

        viewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.setMarket(market);
//        24h change: ▼ 23.09% (+$500)
//       binding.coinVolume.setText("24h change: ▼ "+market.getPrice_change_percentage_24h() +"% ("+market.getPrice_change_24h()+")");

        if (market.getPrice_change_percentage_24h() > 0) {
            binding.coinVolume.setTextColor(Color.parseColor("#72E672"));
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setRoundingMode(RoundingMode.UP);

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(6);

            String marketPercent = formatter.format(market.getPrice_change_24h());
            String volume = decimalFormat.format(market.getPrice_change_percentage_24h());

            binding.coinVolume.setText("24h change: ▲ " + volume + "% (" + marketPercent + ")");
        } else {
            binding.coinVolume.setTextColor(Color.parseColor("#F33333"));
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setRoundingMode(RoundingMode.UP);

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(6);

            String marketPercent = formatter.format(market.getPrice_change_24h());

            String volume = decimalFormat.format(market.getPrice_change_percentage_24h());
            binding.coinVolume.setText("24h change: ▼ " + volume + "% (" + marketPercent + ")");
        }

        String coinName = market.getId();
        String currency = "usd";
        int days = 1;

        viewModel.getChartData(coinName, currency, days);
        ArrayList<Entry> candleEntries = new ArrayList<>();

        ArrayList<String> chartTimeArrayList = new ArrayList<>();

        viewModel.chartDataMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<List<Float>>>() {
            @Override
            public void onChanged(List<List<Float>> lists) {
                candleEntries.clear();
                binding.chart.setData(null);
                for (List<Float> doubles : lists) {

                    long time = Double.valueOf(doubles.get(0)).longValue();

                    Date date = new Date(time);
                    DateFormat formatter = new SimpleDateFormat("HH:mm");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String dateFormatted = formatter.format(date);

//                    chartTimeArrayList.add(dateFormatted);

                    candleEntries.add(new CandleEntry(doubles.get(0), doubles.get(1), doubles.get(2), doubles.get(3), doubles.get(4)));

                }

                Description description = new Description();
                description.setTextColor(Color.parseColor("#808080"));
                description.setTextSize(15f);
                description.setText("Powered by coin gecko api ");

                binding.chart.setDescription(description);
//
                LineDataSet candleDataSet = new LineDataSet(candleEntries, market.getName() + " PRICE CHART");
                candleDataSet.setDrawCircles(false);
                candleDataSet.setCircleRadius(0f);
                candleDataSet.setCubicIntensity(20f);
                candleDataSet.setLineWidth(3f);
                candleDataSet.setDrawValues(false);
                candleDataSet.setValueTextSize(0f);
                candleDataSet.disableDashedLine();
                candleDataSet.setFillAlpha(110);
                candleDataSet.setHighLightColor(R.color.green);
                candleDataSet.setCubicIntensity(0);
                candleDataSet.setGradientColor(Color.parseColor("#03989e"), Color.parseColor("#72E672"));
                candleDataSet.setValueTextColor(Color.parseColor("#000000"));

                LineData cd = new LineData(candleDataSet);

//                Legend legend = binding.chart.getLegend();
//                legend.setEnabled(false);

                binding.chart.setData(cd);
                binding.chart.getXAxis().setDrawGridLines(true);
                binding.chart.setDrawBorders(false);
                binding.chart.getAxisLeft().setDrawGridLines(true);
                binding.chart.getXAxis().setDrawGridLines(false);
                binding.chart.getAxisRight().setDrawGridLines(false);
                binding.chart.setScaleEnabled(false);
                binding.chart.invalidate();
//                binding.chart.sle

                XAxis xAxis = binding.chart.getXAxis();
                xAxis.setEnabled(false);

                YAxis yAxis = binding.chart.getAxisLeft();
                yAxis.setGridLineWidth(0f);
                yAxis.setAxisLineColor(Color.parseColor("#FFFFFF"));
                yAxis.setEnabled(true);

                YAxis yAxis2 = binding.chart.getAxisRight();
                yAxis2.setGridLineWidth(0f);
                yAxis2.setAxisLineColor(Color.parseColor("#FFFFFF"));
                yAxis2.setEnabled(true);

                xAxis.setValueFormatter(new ValueFormatter() {

//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                    @Override
                    public String getFormattedValue(float value) {
                        DateFormat formatter = new SimpleDateFormat("HH-mm");
                        long time = (long) value ;//* 1000L;
                        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

                        return "";
                    }
                });

//                xAxis.setValueFormatter(new SetAXAxis());
                xAxis.setGranularity(1f);
//                binding.chart.
            }
        });


        binding.twentyFourHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getChartData(coinName, currency, days);
            }
        });

        binding.sevenDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int days = 7;

                viewModel.getChartData(coinName, currency, days);
            }
        });

        binding.fourteenDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int days = 14;

                viewModel.getChartData(coinName, currency, days);
            }
        });
        binding.thirtyDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int days = 30;

                viewModel.getChartData(coinName, currency, days);
            }
        });
        binding.ninetyDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int days = 90;

                viewModel.getChartData(coinName, currency, days);
            }
        });
        binding.oneHundredAndEightyDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int days = 180;

                viewModel.getChartData(coinName, currency, days);
            }
        });
        binding.oneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int days =365;

                viewModel.getChartData(coinName, currency, days);
            }
        });

        return binding.getRoot();
    }

    class SetAXAxis extends ValueFormatter {


        String[] chartValues;

//        public SetAXAxis(String[] chartValues) {
//            this.chartValues = chartValues;
//        }


        public SetAXAxis() {
        }

        @Override
        public String getFormattedValue(float value) {

            Date date = new Date(Double.valueOf(value).longValue());
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            return formatter.format(date);
        }
    }

    //// remember to create a class an extendIaxisx
}