package leo.work.library;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import leo.work.morelineandbarchart.chart.MoreLineAndBarChart;
import leo.work.morelineandbarchart.tools.DisplayUtils;

public class MainActivity extends AppCompatActivity {

    private MoreLineAndBarChart moreLineAndBarChart;

    List<Float> value1=new ArrayList<>();
    List<String> bottomValue=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moreLineAndBarChart=findViewById(R.id.main_chart);

        initData();
    }

    private void initData() {
        Random random=new Random();
        List<Float> value2=new ArrayList<>();
        List<Float> value3=new ArrayList<>();
        List<Float> barValue=new ArrayList<>();
        for (int i=0;i<11;i++){
//            value1.add((float) random.nextInt(10));
//            value2.add((float) random.nextInt(4));
//            value3.add((float) random.nextInt(5));
            barValue.add((float) random.nextInt(50));
            bottomValue.add("2023");
        }

        value1.add(3.3f);
        value1.add(3.6f);
        value1.add(3.3f);
        value1.add(3.1f);
        value1.add(3f);
        value1.add(3.9f);
        value1.add(3.5f);
        value1.add(-556.454f);
        value1.add(3.8f);
        value1.add(3.4f);
        value1.add(-1003.4545f);



        moreLineAndBarChart.setBarChartValues(barValue);
//        moreLineAndBarChart.setLinesColors(Color.BLUE);
        moreLineAndBarChart.setIndexTextColor(Color.BLUE);
        moreLineAndBarChart.setBarIndexTextColor(Color.WHITE);
        moreLineAndBarChart.setLineWidth(DisplayUtils.dp2px(this,1));

        moreLineAndBarChart.setSolid(true);
        moreLineAndBarChart.setDrawBar(true);
        moreLineAndBarChart.setShowGrid(true);
        moreLineAndBarChart.setDrawPoint(true);
        moreLineAndBarChart.setShowLineValue(true);

        //.......
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moreLineAndBarChart.setBottomValues(bottomValue);
//                moreLineAndBarChart.setLinesData(value1);
                moreLineAndBarChart.invalidateChart();
            }
        },2000);


    }
}