package com.google.android.gms.samples.vision.face.facetracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Analysis extends AppCompatActivity {
    private LineChart lineChart;
    private PieChart pieChart;
    public static List<Float> result = FaceTrackerActivity.result;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.analysis);
        System.out.println(result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService retrofitExService = retrofit.create(APIService.class);
        System.out.println("noww");
        retrofitExService.getAllResult("lmj").enqueue(new Callback<List<Data>> () {
            @Override
            public void onResponse(@NonNull Call<List<Data>> call, @NonNull Response<List<Data>> response) {
                System.out.println("Twoooo");
                Boolean pos[] = new Boolean[20];
                String res[] = new String[20];
                int first = 1;
                int second = 1;
                int third = 1;
                int fourth = 1;
                int fifth = 1;
                if (response.isSuccessful()) {
                    List<Data> datas = response.body();
                    if (datas != null) {
                        for (int i = 0; i < datas.size(); i++) {
                            Log.e("data" + i, datas.get(i).getResult() + "");
                            pos[i] = datas.get(i).getPosOrNeg();
                            res[i] = datas.get(i).getResult();
                            if (Float.parseFloat(datas.get(i).getResult()) < 0.2)
                                first ++;
                            else if(Float.parseFloat(datas.get(i).getResult()) < 0.4)
                                second++;
                            else if(Float.parseFloat(datas.get(i).getResult()) < 0.6)
                                third++;
                            else if(Float.parseFloat(datas.get(i).getResult()) < 0.8)
                                fourth++;
                            else
                                fifth++;


                        }
                        Log.e("getData2 end", "======================================");
                    }
                }
                pieChart = (PieChart)findViewById(R.id.piechart);

                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);
                pieChart.setExtraOffsets(5,10,5,5);

                pieChart.setDragDecelerationFrictionCoef(0.95f);

                pieChart.setDrawHoleEnabled(false);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);
                pieChart.setEntryLabelColor(Color.WHITE);

                ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                System.out.println(first);
                System.out.println(second);
                System.out.println(third);
                System.out.println(fourth);
                System.out.println(fifth);

                yValues.add(new PieEntry(first,"매우 긍정"));
                yValues.add(new PieEntry(second,"긍정"));
                yValues.add(new PieEntry(third,"보통"));
                yValues.add(new PieEntry(fourth,"부정"));
                yValues.add(new PieEntry(fifth,"매우 부정"));
                //yValues.add(new PieEntry(40f,"Korea"));
                System.out.println("chckck");
                Description descriptionPie = new Description();
                descriptionPie.setText("20회 동안 표정 분포"); //라벨
                descriptionPie.setTextSize(20);
                descriptionPie.setTextColor(Color.WHITE);
                pieChart.setDescription(descriptionPie);
                pieChart.setCenterTextColor(Color.WHITE);

                pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션

                PieDataSet dataSet = new PieDataSet(yValues,"Countries");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                dataSet.setValueTextSize(30f);


                pieChart.setNoDataTextColor(Color.WHITE);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(20f);
                data.setValueTextColor(Color.DKGRAY);

                Legend legendpie = pieChart.getLegend();
                legendpie.setTextColor(Color.WHITE);
                legendpie.setTextSize(13f);

                pieChart.setData(data);

                lineChart = (LineChart)findViewById(R.id.line_chart);

                List<Entry> entries = new ArrayList<>();

//        entries.add(new Entry(1, 1));
//        entries.add(new Entry(2, 2));
//        entries.add(new Entry(3, 0));
//        entries.add(new Entry(4, 4));
//        entries.add(new Entry(5, 3));
                for(int i = 0; i < result.size(); i++){
                    entries.add(new Entry(i + 1, result.get(i)));
                }

                LineDataSet lineDataSet = new LineDataSet(entries, "이번 표정 분포");
                lineDataSet.setLineWidth(2);
                lineDataSet.setCircleRadius(6);
                lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
                lineDataSet.setCircleHoleColor(Color.BLUE);
                lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
                lineDataSet.setDrawCircleHole(true);
                lineDataSet.setDrawCircles(true);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawHighlightIndicators(false);
                lineDataSet.setDrawValues(false);

                lineChart.setGridBackgroundColor(Color.WHITE);
                lineChart.setNoDataTextColor(Color.WHITE);

                LineData lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                lineData.setValueTextColor(Color.WHITE);


                XAxis xAxis = lineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextColor(Color.WHITE);
                xAxis.enableGridDashedLine(8, 24, 0);
                xAxis.setTextSize(13f);

                YAxis yLAxis = lineChart.getAxisLeft();
                yLAxis.setTextColor(Color.WHITE);
                yLAxis.setTextSize(13f);

                YAxis yRAxis = lineChart.getAxisRight();
                yRAxis.setDrawLabels(false);
                yRAxis.setDrawAxisLine(false);
                yRAxis.setDrawGridLines(false);

                Description description = new Description();
                description.setText("");

                Legend legend = lineChart.getLegend();
                legend.setTextColor(Color.WHITE);
                legend.setTextSize(13);

                lineChart.setDoubleTapToZoomEnabled(false);
                lineChart.setDrawGridBackground(false);
                lineChart.setDescription(description);
                lineChart.animateY(2000, Easing.EaseInCubic);
                lineChart.invalidate();

                FaceTrackerActivity.result.clear();
                FaceTrackerActivity.count[0] = 0;
            }

            @Override
            public void onFailure(@NonNull Call<List<Data>> call, @NonNull Throwable t) {
                System.out.println("fail");
                System.out.println(call);
                System.out.println(t);
            }
        });


        //Button btn = (Button) findViewById(R.id.button3);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //FaceTrackerActivity.this.complete();
//            }
//        });
        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.}

//        System.out.println(res[5]);
//        System.out.println(Float.parseFloat(res[5]));
//        for (int i = 0; i < 20; i++) {
//            if (Float.parseFloat(res[i]) < 0.2)
//                first ++;
//            else if(Float.parseFloat(res[i]) < 0.4)
//                second++;
//            else if(Float.parseFloat(res[i]) < 0.6)
//                third++;
//            else if(Float.parseFloat(res[i]) < 0.8)
//                fourth++;
//            else
//                fifth++;
//
//        }

//        pieChart = (PieChart)findViewById(R.id.piechart);
//
//        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(5,10,5,5);
//
//        pieChart.setDragDecelerationFrictionCoef(0.95f);
//
//        pieChart.setDrawHoleEnabled(false);
//        pieChart.setHoleColor(Color.WHITE);
//        pieChart.setTransparentCircleRadius(61f);
//        pieChart.setEntryLabelColor(Color.WHITE);
//
//        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
//
//        System.out.println(first);
//        System.out.println(second);
//        System.out.println(third);
//        System.out.println(fourth);
//        System.out.println(fifth);
//
//        yValues.add(new PieEntry(first,"매우 긍정"));
//        yValues.add(new PieEntry(second,"긍정"));
//        yValues.add(new PieEntry(third,"보통"));
//        yValues.add(new PieEntry(fourth,"부정"));
//        yValues.add(new PieEntry(fifth,"매우 부정"));
//        //yValues.add(new PieEntry(40f,"Korea"));
//        System.out.println("chckck");
//        Description descriptionPie = new Description();
//        descriptionPie.setText("20회 동안 표정 분포"); //라벨
//        descriptionPie.setTextSize(20);
//        descriptionPie.setTextColor(Color.WHITE);
//        pieChart.setDescription(descriptionPie);
//        pieChart.setCenterTextColor(Color.WHITE);
//
//        pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션
//
//        PieDataSet dataSet = new PieDataSet(yValues,"Countries");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        dataSet.setValueTextSize(30f);
//
//
//        pieChart.setNoDataTextColor(Color.WHITE);
//
//        PieData data = new PieData((dataSet));
//        data.setValueTextSize(20f);
//        data.setValueTextColor(Color.YELLOW);
//
//        Legend legendpie = pieChart.getLegend();
//        legendpie.setTextColor(Color.WHITE);
//        legendpie.setTextSize(13f);
//
//        pieChart.setData(data);
//
//        lineChart = (LineChart)findViewById(R.id.line_chart);
//
//        List<Entry> entries = new ArrayList<>();
//
////        entries.add(new Entry(1, 1));
////        entries.add(new Entry(2, 2));
////        entries.add(new Entry(3, 0));
////        entries.add(new Entry(4, 4));
////        entries.add(new Entry(5, 3));
//        for(int i = 0; i < result.size(); i++){
//            entries.add(new Entry(i + 1, result.get(i)));
//        }
//
//        LineDataSet lineDataSet = new LineDataSet(entries, "이번 표정 분포");
//        lineDataSet.setLineWidth(2);
//        lineDataSet.setCircleRadius(6);
//        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
//        lineDataSet.setCircleHoleColor(Color.BLUE);
//        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
//        lineDataSet.setDrawCircleHole(true);
//        lineDataSet.setDrawCircles(true);
//        lineDataSet.setDrawHorizontalHighlightIndicator(false);
//        lineDataSet.setDrawHighlightIndicators(false);
//        lineDataSet.setDrawValues(false);
//
//        lineChart.setGridBackgroundColor(Color.WHITE);
//        lineChart.setNoDataTextColor(Color.WHITE);
//
//        LineData lineData = new LineData(lineDataSet);
//        lineChart.setData(lineData);
//        lineData.setValueTextColor(Color.WHITE);
//
//
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextColor(Color.WHITE);
//        xAxis.enableGridDashedLine(8, 24, 0);
//        xAxis.setTextSize(13f);
//
//        YAxis yLAxis = lineChart.getAxisLeft();
//        yLAxis.setTextColor(Color.WHITE);
//        yLAxis.setTextSize(13f);
//
//        YAxis yRAxis = lineChart.getAxisRight();
//        yRAxis.setDrawLabels(false);
//        yRAxis.setDrawAxisLine(false);
//        yRAxis.setDrawGridLines(false);
//
//        Description description = new Description();
//        description.setText("");
//
//        Legend legend = lineChart.getLegend();
//        legend.setTextColor(Color.WHITE);
//        legend.setTextSize(20);
//
//        lineChart.setDoubleTapToZoomEnabled(false);
//        lineChart.setDrawGridBackground(false);
//        lineChart.setDescription(description);
//        lineChart.animateY(2000, Easing.EaseInCubic);
//        lineChart.invalidate();
//
//        FaceTrackerActivity.result.clear();
//        FaceTrackerActivity.count[0] = 0;

    }

//    public void getSecond(String id, final RetroCallback callback) {
//        apiService.getSecond(id).enqueue(new Callback<List<ResponseGet>>() { @Override public void onResponse(Call<List<ResponseGet>> call, Response<List<ResponseGet>> response) { if (response.isSuccessful()) { callback.onSuccess(response.code(), response.body()); } else { callback.onFailure(response.code()); } } @Override public void onFailure(Call<List<ResponseGet>> call, Throwable t) { callback.onError(t); } }); }
//



}
