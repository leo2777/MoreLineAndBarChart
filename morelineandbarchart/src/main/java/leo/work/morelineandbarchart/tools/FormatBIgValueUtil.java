package leo.work.morelineandbarchart.tools;

import java.util.Locale;

/**
 * 创建人： leo mark
 * 隶属于项目： HuiYing
 * 所位于包下： nom.amixuse.huiying.utils
 * 创建于： 2020 年 12 月 21 10:55 AM
 * 介绍： 大数据格式化雷
 **/
public class FormatBIgValueUtil {

    //必须是排好序的
    private final long[] values= new long[]{1000,10000, 100000000};
    private final String[] units={"k","m","b"};


    public String format(double value) {
        String unit="";
        double adsValue = Math.abs(value);
        int i=values.length-1;
        while (i>=0)
        {
            if(adsValue >values[i]) {
                adsValue /= values[i];
                unit = units[i];
                break;
            }
            i--;
        }
        if (value==0)return "——";
        return value>0?String.format(Locale.getDefault(),"%.2f", adsValue)+unit:"-"+String.format(Locale.getDefault(),"%.2f", adsValue)+unit;
    }
}