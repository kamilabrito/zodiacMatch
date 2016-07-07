package com.kbrtz.zodiacmatch;

import android.util.Log;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by kamilabrito on 7/6/16.
 */
class CalculationsUtils {

    public static int calculateAge (String birthday){

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDate birthdate = new LocalDate(date);
        LocalDate now = new LocalDate();
        Period period = new Period(birthdate, now, PeriodType.yearMonthDay());

        return period.getYears();
    }

    public static int calculateSign (String birthday) {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        try {
            date = format.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Zodiacs> zodiacsList = Arrays.asList(Zodiacs.values());
        for (Zodiacs z :zodiacsList) {
            Interval interval = new Interval(z.getZodiacStart().getTime(), z.getZodiacEnd().getTime());
            if (interval.contains(date.getTime())) {
                return z.getZodiacName();
            }
        }

        return 0;
    }



}
