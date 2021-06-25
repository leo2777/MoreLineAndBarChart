package leo.work.morelineandbarchart.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * 创建人：  leomark
 * 隶属于项目:  WorkLibraryDemo
 * 所位于包下： leo.work.morelineandbarchart.chart
 * 创建于：  2020 年  11 月 05 日   17:22
 * 介绍：多条折线和柱状 图表 绘制自定义view
 **/
public class BaseMoreLineAndBarChart extends View {

    private final static String TAG_ERROR = "MoreLineAndBarChart--error";
    private final static String TAG_WARN = "MoreLineAndBarChart--warn";

    //主页面框
    private Rect mainRect;

    //左边数值画框
    private Rect leftRect;

    //下方的数值画框
    private Rect bottomRect;

    //折线画笔
    private Paint linePaint;

    //柱状图画笔
    private Paint barPaint;

    //点 画笔
    private Paint dotPaint;

    // 文字画笔
    private Paint textPaint;

    //表格画笔
    private Paint gridPaint;

    //底部线画笔
    private Paint bottomLinePaint;

    //背景画笔
    private Paint backgroundPaint;

    //自定义view宽高
    private int displayWidth, displayHeight;

    //最大值和最小值
    private float maxValue, minValue;

    //    左边数值和图标的间距
    private int leftPadding;

    //下方数值和表格的间距
    private int bottomPadding;

    //上方间距
    private int topPadding;

    //折线图数值以及条数集合
    private List<List<Float>> lineValues;

    //下方的文字集合
    private List<String> bottomTextValues;

    //柱状图的集合
    private List<Float> barValues;

    //折线图颜色数组，长度必须和折线条数一样
    private int[] lineColors;

    //柱状图的颜色
    private int barColor;

    //纵坐标某一个值的高度
    private float singleValueY;

    //横坐标某一个值的宽度（按照最长的数据）
    private float singleValueX;

    //左边显示的指标数
    private int leftTargetNum;

    //是否画点
    private boolean isDrawPoint;

    //绘制点是否实心，
    private boolean isSolid;

    //线的宽度
    private float lineWidth;

    //表格线宽度
    private float gridLineWidth;

    //底部线的宽度
    private float bottomLineWidth;

    //是否显示折线图否点的值
    private boolean isShowLineValue;

    //左边指标的字体大小
    private float leftIndexTextSize;

    //下方指标的字体大小
    private float bottomTextSize;

    //折线某点的值字体大小
    private float indexTextSize;

    //默认折线的颜色
    private int lineDefaultColor;

    //左边指标的颜色
    private int leftTextColor;

    //折线某点的颜色
    private int indexTextColor;

    //底部文字颜色
    private int bottomTextColor;

    //分割线颜色
    private int gridColor;

    //底部线的颜色
    private int bottomLineColor;

    //框架背景色
    private int backgroundColor;

    //显示某点值的中心颜色
    private int pointColor;

    //是否显示分割线
    private boolean isShowGrid;

    //是否绘制柱状图（有数据为 前提）
    private boolean isDrawBar;


    public BaseMoreLineAndBarChart(Context context) {
        this(context, null);
    }

    public BaseMoreLineAndBarChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public BaseMoreLineAndBarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObject();
    }


    /**
     * 初始化所用到的对象
     */
    private void initObject() {

        linePaint = new Paint(ANTI_ALIAS_FLAG);
        dotPaint = new Paint(ANTI_ALIAS_FLAG);
        textPaint = new Paint(ANTI_ALIAS_FLAG);
        barPaint = new Paint(ANTI_ALIAS_FLAG);
        bottomLinePaint = new Paint(ANTI_ALIAS_FLAG);
        gridPaint = new Paint(ANTI_ALIAS_FLAG);
        backgroundPaint = new Paint(ANTI_ALIAS_FLAG);


        maxValue = Float.MIN_VALUE;
        minValue = Float.MAX_VALUE;
        singleValueY = 0;
        singleValueX = 0;

        displayWidth = 0;
        displayHeight = 0;

        lineColors = new int[0];
        lineValues = new ArrayList<>();
        barValues = new ArrayList<>();
        bottomTextValues = new ArrayList<>();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        displayWidth = w;
        displayHeight = h;
        setRect();
    }

    /**
     * 设置每个框的位置
     */
    private void setRect() {
        int leftWidth = (int) (displayWidth * 0.1f);
        int mainHeight = (int) (displayHeight * 0.9f);


        mainRect = new Rect(leftWidth + leftPadding, topPadding, displayWidth - leftPadding, mainHeight - bottomPadding);
        leftRect = new Rect(0, topPadding, leftWidth, mainHeight - bottomPadding);
        bottomRect = new Rect(leftWidth + leftPadding, mainHeight - bottomPadding, displayWidth, displayHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        backgroundPaint.setColor(backgroundColor);
        canvas.drawColor(backgroundPaint.getColor());
        if (displayWidth == 0 || displayHeight == 0 || mainRect.height() == 0) {
            return;
        }
        //计算位置
        calculateValue();
        //绘制左边指标以及分割线
        drawLeftAndGrid(canvas);
        //绘制柱状图
        drawBarChart(canvas);
        //绘制折线图
        drawLineChart(canvas);
        //绘制底部指标
        drawBottom(canvas);
    }


    /**
     * 计算所需要的的数值
     */
    private void calculateValue() {

        int maxLineNum = 0;

        //计算所有折线图的最高最低值
        if (lineValues.size() > 0) {
            for (int i = 0; i < lineValues.size(); i++) {
                for (int j = 0; j < lineValues.get(i).size(); j++) {
                    maxValue = Math.max(lineValues.get(i).get(j), maxValue);
                    minValue = Math.min(lineValues.get(i).get(j), minValue);
                }
                maxLineNum = Math.max(lineValues.get(i).size(), maxLineNum);
            }
            //单一值的宽度
            singleValueX = (mainRect.width()) / maxLineNum;
        }


        //计算柱状图所有数据的最高最低值
        for (int i = 0; i < barValues.size(); i++) {
            maxValue = Math.max(barValues.get(i), maxValue);
            minValue = Math.min(barValues.get(i), minValue);
        }

        //单一值的高度
        singleValueY = (mainRect.height() - lineWidth * 3 - topPadding - bottomPadding) / (maxValue - minValue);
    }

    /**
     * 画左边的值
     *
     * @param canvas
     */
    @SuppressLint("LongLogTag")
    private void drawLeftAndGrid(Canvas canvas) {
        textPaint.setColor(leftTextColor);
        textPaint.setTextSize(leftIndexTextSize);
        gridPaint.setColor(gridColor);
        gridPaint.setStrokeWidth(gridLineWidth);
        //某一个值的数值
        float singleValue = (maxValue - minValue) / leftTargetNum;
        float singleY = singleValueY*singleValue;


        textPaint.measureText(String.format("%.2f", minValue));
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;

        //设置默认值
        if (leftTargetNum == 0) {
            leftTargetNum = 5;
        }

        //设置错误的错误值
        if (textHeight * leftTargetNum > leftRect.height()) {
            Log.e(TAG_WARN, "drawLeft: init-warn :    设置的左边指标值过多，为了显示效果，已经重置为五个，请重新检查！");
            leftTargetNum = 5;
        }

        //画值
        for (int i = 0; i <= leftTargetNum; i++) {
            String value = String.format("%.2f", minValue+singleValue * i);
            float textWidth = textPaint.measureText(value);
            float y = leftRect.bottom-bottomPadding-singleY*i;
            //测量高度
            fm = textPaint.getFontMetrics();
            textHeight = fm.descent - fm.ascent;
            //画值
            canvas.drawText(value, leftRect.width() / 2 - textWidth / 2, y+textHeight/2, textPaint);
            //画横向表格线
            if (isShowGrid) {
                canvas.drawLine(leftRect.right, y,
                        mainRect.right - leftPadding, y, gridPaint);
            }
        }

    }


    /**
     * 绘制折线图
     *
     * @param canvas
     */
    private void drawLineChart(Canvas canvas) {
        if (lineColors.length > 0 && lineColors.length != lineValues.size()) {
            throw new RuntimeException(TAG_ERROR + "所设置的颜色值与设置的折线图条数不一致，请检查之后重试！");
        }

        linePaint.setStrokeWidth(lineWidth);
        textPaint.setColor(indexTextColor);
        textPaint.setTextSize(indexTextSize);
        for (int i = 0; i < lineValues.size(); i++) {
            linePaint.setColor(lineColors.length == 0 ? lineDefaultColor : lineColors[i]);
            //画折线以及值
            for (int j = 0; j < lineValues.get(i).size(); j++) {
                float lastX = j == 0 ? mainRect.left + singleValueX / 2 : mainRect.left + singleValueX / 2 + ((j - 1) * singleValueX);
                float currentX = mainRect.left + singleValueX / 2 + (j * singleValueX);
                float lastValue = j == 0 ? lineValues.get(i).get(0)-minValue : lineValues.get(i).get(j - 1)-minValue;
                float currentValue = lineValues.get(i).get(j)-minValue;
                canvas.drawLine(lastX, mainRect.height() - (singleValueY * lastValue), currentX, mainRect.height() - (singleValueY * currentValue), linePaint);

            }

            float textWidth = textPaint.measureText(maxValue + "");
            boolean isDrawLineValue = mainRect.width() / (lineValues.get(i).size()>1?lineValues.get(i).size()-1:lineValues.get(i).size()) > textWidth;

//            Log.e("222222", "drawLineChart: 字体宽度：" + textWidth + " 显示的宽度：" + mainRect.width() / (lineValues.get(i).size()));
            //画点和值
            for (int j = 0; j < lineValues.get(i).size(); j++) {
                float currentX = mainRect.left + singleValueX / 2 + (j * singleValueX);
                float currentValue = lineValues.get(i).get(j)-minValue;


                if (isShowLineValue && isDrawLineValue) {
                    textWidth = textPaint.measureText(String.format("%.1f", lineValues.get(i).get(j)));
                    canvas.drawText(String.format("%.2f", lineValues.get(i).get(j)), currentX - textWidth / 2, mainRect.height() - (singleValueY * currentValue) - lineWidth * 4, textPaint);
                }
                if (isDrawPoint) {
                    dotPaint.setColor(lineColors.length == 0 ? lineDefaultColor : lineColors[i]);
                    dotPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(currentX, mainRect.height() - (singleValueY * currentValue), lineWidth * 3, dotPaint);
                }
                if (isSolid) {
                    dotPaint.setColor(pointColor);
                    dotPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(currentX, mainRect.height() - (singleValueY * currentValue), lineWidth * 2, dotPaint);
                }
            }
        }
    }

    /**
     * 绘制柱状图表格
     *
     * @param canvas
     */
    private void drawBarChart(Canvas canvas) {
        if (barValues.size() == 0) {
            return;
        }

        barPaint.setColor(barColor);
        //计算柱状图的宽度
        float barWidth = (mainRect.width()) / barValues.size();
        float emptyWidth = barWidth / 4;
        barPaint.setColor(barColor);
        for (int i = 0; i < barValues.size(); i++) {
            float x = mainRect.left + barWidth * i;
            canvas.drawRect(x + emptyWidth, mainRect.height() - (singleValueY * barValues.get(i)), x + barWidth - emptyWidth, bottomRect.top, barPaint);
        }

    }

    /**
     * 绘制下方的时间值
     *
     * @param canvas
     */
    @SuppressLint("LongLogTag")
    private void drawBottom(Canvas canvas) {

        bottomLinePaint.setStrokeWidth(bottomLineWidth);
        bottomLinePaint.setColor(bottomLineColor);

        if (bottomTextValues.size() == 0) {
            Log.e(TAG_WARN, "drawBottom: 您并没有添加底部指标数据！");
            return;
        }

        textPaint.setColor(bottomTextColor);
        textPaint.setTextSize(bottomTextSize);
        String judgeText = bottomTextValues.get(0);
        float textWith = textPaint.measureText(judgeText);
        //绘制底部线
        canvas.drawLine(leftRect.right, mainRect.bottom, mainRect.right, mainRect.bottom, bottomLinePaint);
        if (mainRect.width() / bottomTextValues.size() > textWith) {
            float x = mainRect.width() / bottomTextValues.size();
            for (int i = 0; i < bottomTextValues.size(); i++) {
                canvas.drawText(bottomTextValues.get(i), bottomRect.left + x / 2 + (x * i - textWith / 2), bottomRect.top + bottomRect.height() / 2, textPaint);
            }
            return;
        }

        int showSum = getBottomShowSum(bottomRect.width() - textWith / 2, textWith, bottomTextValues.size());
        int index =bottomTextValues.size() / showSum + 1;
        float x = (bottomRect.width() - textWith / 2) / showSum+1;
        for (int i = 1; i < showSum; i++) {
            textWith = textPaint.measureText(bottomTextValues.get(i));
            canvas.drawText(bottomTextValues.get(index * i), bottomRect.left + (x * i - (textWith / 2) + (singleValueX / 2)), bottomRect.top + bottomRect.height() / 2, textPaint);
        }

        textWith = textPaint.measureText(bottomTextValues.get(0));
        canvas.drawText(bottomTextValues.get(0), bottomRect.left - (textWith / 2) + (singleValueX / 2), bottomRect.top + bottomRect.height() / 2, textPaint);
        textWith = textPaint.measureText(bottomTextValues.get(bottomTextValues.size() - 1));
        canvas.drawText(bottomTextValues.get(bottomTextValues.size() - 1), bottomRect.right - textWith, bottomRect.top + bottomRect.height() / 2, textPaint);


    }


    /**
     * 获取底部显示多少个值
     *
     * @param width     显示的总长度
     * @param textWith  文字的宽度
     * @param valueSize 数据的总数
     * @return 返回 显示的个数
     */
    private int getBottomShowSum(float width, float textWith, int valueSize) {
        //当可以被5除尽
        if (valueSize % 5 == 0 && textWith * 5 < width - textWith) {
            return 4;
        }
        if (valueSize % 4 == 0 && textWith * 4 < width - textWith) {
            return 3;
        }
        if (valueSize % 3 == 0 && textWith * 3 < width - textWith) {
            return 2;
        }
        return 1;

    }


    /**
     * 设置属性
     */

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    protected void setLineValues(List<List<Float>> lineValues) {
        this.lineValues = lineValues;
    }

    protected void setBottomTextValues(List<String> bottomTextValues) {
        this.bottomTextValues = bottomTextValues;
    }

    protected void setBarValues(List<Float> barValues) {
        this.barValues = barValues;
    }

    protected void setLineColors(int[] lineColors) {
        this.lineColors = lineColors;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public void setDrawPoint(boolean drawPoint) {
        isDrawPoint = drawPoint;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setGridLineWidth(float gridLineWidth) {
        this.gridLineWidth = gridLineWidth;
    }

    public void setBottomLineWidth(float bottomLineWidth) {
        this.bottomLineWidth = bottomLineWidth;
    }

    public void setShowLineValue(boolean showLineValue) {
        isShowLineValue = showLineValue;
    }

    public void setLeftTargetNum(int leftTargetNum) {
        this.leftTargetNum = leftTargetNum;
    }

    public void setLeftIndexTextSize(float leftIndexTextSize) {
        this.leftIndexTextSize = leftIndexTextSize;
    }

    public void setBottomTextSize(float bottomTextSize) {
        this.bottomTextSize = bottomTextSize;
    }

    public void setIndexTextSize(float indexTextSize) {
        this.indexTextSize = indexTextSize;
    }

    public void setLineDefaultColor(int lineDefaultColor) {
        this.lineDefaultColor = lineDefaultColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
    }

    public void setIndexTextColor(int indexTextColor) {
        this.indexTextColor = indexTextColor;
    }

    public void setBottomTextColor(int bottomTextColor) {
        this.bottomTextColor = bottomTextColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    public void setBottomLineColor(int bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    public void setShowGrid(boolean showGrid) {
        isShowGrid = showGrid;
    }


    public void setDrawBar(boolean drawBar) {
        isDrawBar = drawBar;
    }

    /**
     * 刷新，设置数据之后必须调用刷新，否则不生效
     */
    protected void invalidateMoreLineAndBarChart() {
        invalidate();
    }

}
