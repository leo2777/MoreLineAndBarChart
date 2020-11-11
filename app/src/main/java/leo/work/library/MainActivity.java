package leo.work.library;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import leo.work.morelineandbarchart.chart.BaseMoreLineAndBarChart;
import leo.work.morelineandbarchart.chart.MoreLineAndBarChart;
import leo.work.morelineandbarchart.tools.DisplayUtils;

public class MainActivity extends AppCompatActivity {

    private MoreLineAndBarChart moreLineAndBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moreLineAndBarChart=findViewById(R.id.main_chart);

        initData();
    }

    private void initData() {
        Random random=new Random();
        List<Float> value1=new ArrayList<>();
        List<Float> value2=new ArrayList<>();
        List<Float> value3=new ArrayList<>();
        List<Float> barValue=new ArrayList<>();
        List<String > bottomValue=new ArrayList<>();
        for (int i=0;i<300;i++){
            value1.add((float) random.nextInt(60));
            value2.add((float) random.nextInt(40));
            value3.add((float) random.nextInt(5));
            barValue.add((float) random.nextInt(50));
            bottomValue.add("指标");
        }


        moreLineAndBarChart.setBottomValues(bottomValue);
        moreLineAndBarChart.setLinesData(value1,value2,value3);
        moreLineAndBarChart.setBarChartValues(barValue);
        moreLineAndBarChart.setLinesColors(Color.BLUE,Color.YELLOW,Color.RED);
        moreLineAndBarChart.setLineWidth(DisplayUtils.dp2px(this,1));

        moreLineAndBarChart.setSolid(true);
        moreLineAndBarChart.setDrawBar(true);
//        moreLineAndBarChart.setShowGrid(true);
        moreLineAndBarChart.setDrawPoint(true);
//        moreLineAndBarChart.setShowLineValue(true);

        //.......

        moreLineAndBarChart.invalidateChart();


    }
}