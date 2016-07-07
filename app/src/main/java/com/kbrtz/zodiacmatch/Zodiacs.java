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

    TAURUS(R.string.taurus_name, zodiacDate("20/04"), zodiacDate("20/05"), R.drawable.taurus),

    GEMINI(R.string.gemini_name, zodiacDate("21/05"), zodiacDate("20/06"), R.drawable.gemini),

    CANCER(R.string.cancer_name, zodiacDate("21/06"), zodiacDate("21/07"), R.drawable.cancer),

    LEO(R.string.leo_name, zodiacDate("22/07"), zodiacDate("22/08"), R.drawable.leo),

    VIRGO(R.string.virgo_name, zodiacDate("23/08"), zodiacDate("22/09"), R.drawable.virgo),

    LIBRA(R.string.libra_name, zodiacDate("23/09"), zodiacDate("22/10"), R.drawable.libra),

    SCORPIO(R.string.scorpio_name, zodiacDate("23/10"), zodiacDate("21/11"), R.drawable.scorpio),

    SAGITTARIUS(R.string.sagittarius_name, zodiacDate("22/11"), zodiacDate("21/12"), R.drawable.sagittarius),

    CAPRICORN(R.string.capricorn_name, zodiacDate("22/12"), zodiacDate("20/01"), R.drawable.capricorn),

    AQUARIUS(R.string.aquarius_name, zodiacDate("21/01"), zodiacDate("19/02"), R.drawable.aquarius),

    PISCES(R.string.pices_name, zodiacDate("20/02"), zodiacDate("20/03"), R.drawable.pisces);

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
        DateFormat df = new SimpleDateFormat("dd/MM");
        df.setLenient(false);
        Date dt = null;
        try {
            dt = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

}
