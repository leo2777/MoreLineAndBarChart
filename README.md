# MoreLineAndBarChart
# 一款多条折线与柱状图共存图表 [![](https://jitpack.io/v/leo2777/MoreLineAndBarChart.svg)](https://jitpack.io/#leo2777/MoreLineAndBarChart)
***
## 废话不多说，先上效果图

#### 本框架提供图表组合实现，集成之后你可以实现：

![示例图1](https://github.com/leo2777/MoreLineAndBarChart/blob/master/demoImg/img1.png)     ![示例图2](https://github.com/leo2777/MoreLineAndBarChart/blob/master/demoImg/img2.png)

![示例图3](https://github.com/leo2777/MoreLineAndBarChart/blob/master/demoImg/img3.png)     ![示例图4](https://github.com/leo2777/MoreLineAndBarChart/blob/master/demoImg/img4.png)

![示例图5](https://github.com/leo2777/MoreLineAndBarChart/blob/master/demoImg/img5.png)     ![示例图6](https://github.com/leo2777/MoreLineAndBarChart/blob/master/demoImg/img6.png)








- 显示柱状图表（图一）
- 显示多条折线图（图二）
- 显示单条折线图（图三）
- 显示折线图每个点，以及设置实心空心（图四）
- 柱状图和多条折线图一起显示（图五）
- 显示所有效果（图六）





## 简单使用

#### 1. 首先在项目中添加 jitpack.io 库

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### 2. 然后在项目中添加依赖

```gradle
dependencies {
	        implementation 'com.github.leo2777:MoreLineAndBarChart:1.0.2'
	}
 
 ``` 
 
 #### 3. 在想要实现的布局当中添加控件
 
```xml
  <leo.work.morelineandbarchart.chart.MoreLineAndBarChart
        android:id="@+id/main_chart"
        android:layout_width="match_parent"
        android:layout_height="300dp"/>
```

#### 4. 绑定控件之后即可设置数据

```java


        moreLineAndBarChart=findViewById(R.id.main_chart);
	
	
        //.......
	
	//设置假数据
	Random random=new Random();
        List<Float> value1=new ArrayList<>();
        List<Float> value2=new ArrayList<>();
        List<Float> value3=new ArrayList<>();
        List<Float> barValue=new ArrayList<>();
        List<String > bottomValue=new ArrayList<>();
        for (int i=0;i<5;i++){
            value1.add((float) random.nextInt(60));
            value2.add((float) random.nextInt(40));
            value3.add((float) random.nextInt(5));
            barValue.add((float) random.nextInt(50));
            bottomValue.add("指标");
        }
	
	
        moreLineAndBarChart.setBottomValues(bottomValue);//设置底部数据
        moreLineAndBarChart.setLinesData(value1,value2,value3);//设置折线图数据，
        moreLineAndBarChart.setBarChartValues(barValue);//设置柱状图数据
        moreLineAndBarChart.setLinesColors(Color.BLUE,Color.YELLOW,Color.RED);//设置折线的颜色，不设置会有默认，但是一旦设置需和上面的折线图的条数一样。
        moreLineAndBarChart.setLineWidth(4f);//设置折线图宽度

        moreLineAndBarChart.setDrawPoint(true);//绘制点
        moreLineAndBarChart.setSolid(true);//设置是否空心，搭配绘制点
        moreLineAndBarChart.setDrawBar(true);//设置是否绘制柱状图，不添加柱状图数据也有同样效果
        moreLineAndBarChart.setShowGrid(true);//是否绘制表格线，目前只有横向分割线
        moreLineAndBarChart.setShowLineValue(true);//是否显示折线图每个点的数值（当点太多的时候不生效）

        //.......等等，具体看下方

        //设置完数据之后调用刷新，否则不生效。
        moreLineAndBarChart.invalidateChart();
```






## 进阶使用

#### 1. 可以直接在布局文件当中设置需要的属性

```xml
    <leo.work.morelineandbarchart.chart.MoreLineAndBarChart
        android:id="@+id/main_chart"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:chart_is_slide_point="true"
        app:chart_bottom_text_color="@color/colorPrimary"
        app:chart_is_show_grid="true"
        app:chart_bottom_padding="10dp"
        app:chart_line_size="1dp"/>
```
 

#### 2. 框架内可以设置的属性

```xml
    //左边显示的指标数
        <attr name="chart_left_index_num" format="integer"/>


        //左边指数的字体大小
        <attr name="chart_left_index_text_size" format="dimension"/>
        //下方指标的字体大小
        <attr name="chart_bottom_text_size" format="dimension"/>
        //中间某一点的值具体大小
        <attr name="chart_center_text_size" format="dimension"/>
        //折线图线的大小
        <attr name="chart_line_size" format="dimension"/>
        //表格分割线具体大小
        <attr name="chart_grid_size" format="dimension"/>
        //底部线的具体大小
        <attr name="chart_bottom_line_size" format="dimension"/>
        //左边指标的距离
        <attr name="chart_left_index_padding" format="dimension"/>
        //图表上方的距离
        <attr name="chart_chart_top_padding" format="dimension"/>
        //图表下方的距离
        <attr name="chart_bottom_padding" format="dimension"/>




        //左边指标的具体颜色
        <attr name="chart_left_index_text_color" format="color"/>
        //中间值的颜色
        <attr name="chart_center_index_text_color" format="color"/>
        //下方指标的颜色
        <attr name="chart_bottom_text_color" format="color"/>
        //柱状图的颜色
        <attr name="chart_bar_color" format="color"/>
        //分割线的颜色
        <attr name="chart_grid_color" format="color"/>
        //底部线的颜色
        <attr name="chart_bottom_line_color" format="color"/>
        //折线的颜色
        <attr name="chart_line_color" format="color"/>
        //图表的背景颜色
        <attr name="chart_background_color" format="color"/>
        //中心点的颜色（点为空心）
        <attr name="chart_line_point_center_color" format="color"/>




        //是否显示柱状图
        <attr name="chart_is_show_bar_chart" format="boolean"/>
        //是否画点
        <attr name="chart_is_draw_point" format="boolean"/>
        //绘制的点是否空心
        <attr name="chart_is_slide_point" format="boolean"/>
        //是否显示折线图都一点的值
        <attr name="chart_is_show_index_text" format="boolean"/>
        //是否显示分割线
        <attr name="chart_is_show_grid" format="boolean"/>
```
  
  

框架出现问题，或者想提出改进的，欢迎骚扰 QQ:2549732107 

<img src="https://github.com/leo2777/MoreLineAndBarChart/blob/master/demoImg/qq.png" width = "200" height = "240" alt="QQ"/>                 <img src="https://github.com/leo2777/MoreLineAndBarChart/blob/master/demoImg/wechet.png" width = "200" height = "240" alt="微信"/>







