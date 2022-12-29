package com.truemega.solfa.view.utils;

//import com.egabi.bpmapplication.bpm.utils.ApplicationLogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    public static final String DATE_PATTERN = "yyyy/MM/dd";
    




    public static oracle.jbo.domain.Date convertJavaUtilDateToOracleDate(java.util.Date javaUtilDate)
      {
        if(javaUtilDate == null)
          return null;
      
        long javaMilliseconds = javaUtilDate.getTime();
        java.sql.Date javaSqlDate = new java.sql.Date(javaMilliseconds);
        return new oracle.jbo.domain.Date(javaSqlDate);
      }

    public static String convertJboGroToHijri(oracle.jbo.domain.Date gregorianDate) {
        java.util.Date utilDate = DateUtils.getUtilDateFromJboDate(gregorianDate);
//        ApplicationLogger.log("convertJboGroToHijri:gregorianDate"+gregorianDate, ApplicationLogger.DEBUG);
//        ApplicationLogger.log("convertJboGroToHijri:utilDate"+utilDate, ApplicationLogger.DEBUG);
        
        
        String _year = new SimpleDateFormat("yyyy").format(utilDate);
        String _month = new SimpleDateFormat("MM").format(utilDate);
        String _day = new SimpleDateFormat("dd").format(utilDate);
        
        //String _time = new SimpleDateFormat("hh:mm:ss a").format(gregorianDate);
        //ApplicationLogger.log("Time  : " + _time, ApplicationLogger.DEBUG);
        int year = Integer.parseInt(_year);
        int month = Integer.parseInt(_month);
        int day = Integer.parseInt(_day);

        //Convert from Input Gregorian to Hijri
        HijrahDate hijriDate = HijrahChronology.INSTANCE.date(LocalDate.of(year, month, day));
        //ApplicationLogger.log("Hijri Date : " + hijriDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)), ApplicationLogger.DEBUG);
        
        return hijriDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public static oracle.jbo.domain.Date convertHijriToJboGro(Object hijriDate) {
        //String hijriDate = "14/10/1437";
        int year=0  ;
        int month=0 ;
        int day=0 ;
        oracle.jbo.domain.Date jboDate;
        
        
        DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        try {
            if(hijriDate == null || hijriDate.equals("")){
                return null;
            }
            java.util.Date startDate = (java.util.Date) formatter.parse(hijriDate+"");
            String __year = new SimpleDateFormat("yyyy").format(startDate);
            String __month = new SimpleDateFormat("MM").format(startDate);
            String __day = new SimpleDateFormat("dd").format(startDate);
            
            year = Integer.parseInt(__year);
            month = Integer.parseInt(__month);
            day = Integer.parseInt(__day);

//            ApplicationLogger.log("Year : " + year, ApplicationLogger.DEBUG);
//            ApplicationLogger.log("Month : " + month, ApplicationLogger.DEBUG);
//            ApplicationLogger.log("Day : " + day, ApplicationLogger.DEBUG);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Convert from Input Hijri to Gregorian
        HijrahDate convertHijriDate = HijrahDate.of(year, month, day);
        LocalDate localDate = IsoChronology.INSTANCE.date(convertHijriDate);
        localDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
//        ApplicationLogger.log("Gregorian Date : " + localDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)), ApplicationLogger.DEBUG);
        try {
            java.util.Date utilDate = formatter.parse(localDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            jboDate = new oracle.jbo.domain.Date(sqlDate);
            
//            ApplicationLogger.log("jboDate"+jboDate, ApplicationLogger.DEBUG);
            
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


        return jboDate;
    }
    
    public static oracle.jbo.domain.Date getJboDateFromStringDate(String Date) {
        int year = 0;
        int month = 0;
        int day = 0;
        oracle.jbo.domain.Date jboDate;


        DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        try {
            java.util.Date utilDate = formatter.parse(Date);
//            ApplicationLogger.log("UtilDate>>>>>>>>"+utilDate, ApplicationLogger.DEBUG);
            
            jboDate = DateUtils.getJboDateFromUtilDate(utilDate);
//            ApplicationLogger.log("JboDate>>>>>"+jboDate, ApplicationLogger.DEBUG);
        } catch (ParseException e) {
//            ApplicationLogger.log("Can't convert >>>>>>>>>>> getJboDateFromStringDate", ApplicationLogger.DEBUG);
            e.printStackTrace();
            return null;
        }


        return jboDate;
    }


    public static oracle.jbo.domain.Date getJboDateFromUtilDate(java.util.Date utilDate) {
        oracle.jbo.domain.Date jboDate = null;
        if (utilDate != null) {
            jboDate = new oracle.jbo.domain.Date(DateUtils.getSqlDateFromUtilDate(utilDate));
        }
        return jboDate;
    }

    public static oracle.jbo.domain.Date getJboDateFromSqlDate(java.sql.Date sqlDate) {
        oracle.jbo.domain.Date jboDate = null;
        if (sqlDate != null) {
            jboDate = new oracle.jbo.domain.Date(sqlDate);
        }
        return jboDate;
    }

    public static java.sql.Date getSqlDateFromJboDate(oracle.jbo.domain.Date jboDate) {
        return jboDate.dateValue();
    }

    public static java.sql.Date getSqlDateFromUtilDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    public static java.util.Date getUtilDateFromJboDate(oracle.jbo.domain.Date jboDate) {
        java.util.Date utilDate = null;
        if (jboDate != null) {
            utilDate = new java.util.Date(jboDate.dateValue().getTime());
        }
        return utilDate;
    }

    public static java.util.Date getUtilDateFromSqlDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }

}
