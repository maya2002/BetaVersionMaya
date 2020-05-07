package com.example.betaversionmaya2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Serv {
    public static String readDateTime() {
        Calendar calendar;
        String dt;
        calendar = Calendar.getInstance();
        SimpleDateFormat sDF = new SimpleDateFormat("yyMMddHHmm");
        dt = sDF.format(calendar.getTime());
        return dt;
    }

    public static String viewDateTime(String dt) {
        String vdt=dt.substring(4,6)+"/"+dt.substring(2,4)+"/"+dt.substring(0,2)+"   "+dt.substring(6,8)+":"+dt.substring(8);
        return vdt;
    }
}
