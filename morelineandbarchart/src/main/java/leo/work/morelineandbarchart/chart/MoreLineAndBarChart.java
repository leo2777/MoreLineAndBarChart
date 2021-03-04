package leo.work.morelineandbarchart.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;

import leo.work.morelineandbarchart.R;

/**
 * 创建人：  leomark
 * 隶属于项目:  WorkLibraryDemo
 * 所位于包下： leo.work.morelineandbarchart.chart
 * 创建于：  2020 年  11 月 05 日   17:20
 * 介绍：多条线和柱状 图表设置类
 **/
public class MoreLineAndBarChart extends BaseMoreLineAndBarChart {


    public MoreLineAndBarChart(Context context) {
      this(context,null);
    }

    public MoreLineAndBarChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoreLineAndBarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    /**
     *  初始化值
     * @param attrs
     */
    private void initAttr(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MoreLineAndBarChart);
        try {
            setLeftTargetNum(array.getInteger(R.styleable.MoreLineAndBarChart_chart_left_index_num,5));
            setLeftIndexTextSize(array.getDimension(R.styleable.MoreLineAndBarChart_chart_left_index_text_size,getDimension(R.dimen.chart_left_text_size)));
            setBottomTextSize(array.getDimension(R.styleable.MoreLineAndBarChart_chart_bottom_text_size,getDimension(R.dimen.chart_bottom_text_size)));
            setIndexTextSize(array.getDimension(R.styleable.MoreLineAndBarChart_chart_center_text_size,getDimension(R.dimen.chart_center_text_size)));
            setLineWidth(array.getDimension(R.styleable.MoreLineAndBarChart_chart_line_size,getDimension(R.dimen.chart_line_size)));
            setGridLineWidth(array.getDimension(R.styleable.MoreLineAndBarChart_chart_grid_size,getDimension(R.dimen.chart_grid_size)));
            setBottomLineWidth(array.getDimension(R.styleable.MoreLineAndBarChart_chart_bottom_line_size,getDimension(R.dimen.chart_bottom_line_size)));
            setLeftTextColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_left_index_text_color,getColor(R.color.chart_left_index_text_color)));
            setIndexTextColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_center_index_text_color,getColor(R.color.chart_center_index_color)));
            setBottomTextColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_bottom_text_color,getColor(R.color.chart_bottom_text_color)));
            setBarColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_bar_color,getColor(R.color.chart_bar_color)));
            setGridColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_grid_color,getColor(R.color.chart_grid_color)));
            setBottomLineColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_bottom_line_color,getColor(R.color.chart_bottom_line_color)));
            setLineDefaultColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_line_color,getColor(R.color.chart_line_color)));
            setBackgroundColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_background_color,getColor(R.color.chart_bg_color)));
            setPointColor(array.getColor(R.styleable.MoreLineAndBarChart_chart_line_point_center_color,getColor(R.color.chart_chart_point_color)));
            setDrawPoint(array.getBoolean(R.styleable.MoreLineAndBarChart_chart_is_draw_point,false));
            setShowLineValue(array.getBoolean(R.styleable.MoreLineAndBarChart_chart_is_show_index_text,false));
            setShowGrid(array.getBoolean(R.styleable.MoreLineAndBarChart_chart_is_show_grid,true));
            setSolid(array.getBoolean(R.styleable.MoreLineAndBarChart_chart_is_slide_point,false));
            setDrawBar(array.getBoolean(R.styleable.MoreLineAndBarChart_chart_is_show_bar_chart,false));
            setTopPadding(array.getDimensionPixelSize(R.styleable.MoreLineAndBarChart_chart_chart_top_padding,getDimensionPixel(R.dimen.chart_top_padding)));
            setBottomPadding(array.getDimensionPixelSize(R.styleable.MoreLineAndBarChart_chart_bottom_padding,getDimensionPixel(R.dimen.chart_bottom_padding)));
            setLeftPadding(array.getDimensionPixelSize(R.styleable.MoreLineAndBarChart_chart_left_index_padding,getDimensionPixel(R.dimen.chart_left_padding)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    private int getDimensionPixel(@DimenRes int resId){
        return getResources().getDimensionPixelSize(resId);
    }

    private float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    private int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(),resId);
    }

    /**
     * 设置折线图的数据，可多条，
     * @param linesData float 集合
     */
    @SafeVarargs
    public final void setLinesData(List<Float>... linesData){
        setLineValues(Arrays.asList(linesData));
    }

    /**
     *  设置折线图的颜色，如果设置，将必须与有几条折线的数组相对应也有几条
     * @param linesColors
     */
    public void setLinesColors(int ... linesColors){
        setLineColors(linesColors);
    }

    public void setBarChartValues(List<Float> barChartValues){
        setBarValues(barChartValues);
    }

    /**
     *  设置下方显示的指标
     * @param bottomValues
     */
    public void setBottomValues(List<String > bottomValues){
        setBottomTextValues(bottomValues);
    }

    public void invalidateChart(){
        invalidateMoreLineAndBarChart();
    }


}
