package com.kbrtz.zodiacmatch;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kamila.brito on 09/05/2016.
 */
public enum Zodiacs {

    ARIES(R.string.aries_name, zodiacDate("21/03"), zodiacDate("19/04"), R.drawable.aries),

    TAURUS(R.string.aries_name, zodiacDate("20/04"), zodiacDate("20/05"), R.drawable.aries);

    public int zodiacName;

    public int zodiacImage;

    public Date zodiacStart;

    public Date zodiacEnd;

    Zodiacs(int zodiacName, Date zodiacStart, Date zodiacEnd, int zodiacImage) {
        this.zodiacName = zodiacName;
        this.zodiacStart = zodiacStart;
        this.zodiacEnd = zodiacEnd;
        this.zodiacImage = zodiacImage;
    }

    public int getZodiacImage() {
        return zodiacImage;
    }

    public void setZodiacImage(int zodiacImage) {
        this.zodiacImage = zodiacImage;
    }

    public int getZodiacName() {
        return zodiacName;
    }

    public void setZodiacName(int zodiacName) {
        this.zodiacName = zodiacName;
    }

    public Date getZodiacEnd() {
        return zodiacEnd;
    }

    public void setZodiacEnd(Date zodiacEnd) {
        this.zodiacEnd = zodiacEnd;
    }

    public Date getZodiacStart() {
        return zodiacStart;
    }

    public void setZodiacStart(Date zodiacStart) {
        this.zodiacStart = zodiacStart;
    }

    public static Date zodiacDate(String date) {
        DateFormat df = new SimpleDateFormat ("dd/MM");
        df.setLenient (false);
        Date dt = null;
        try {
            dt = df.parse (date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

}
