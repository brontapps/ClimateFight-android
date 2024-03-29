package com.climate.fight;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

import androidx.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HelperTiempos {

    private final Context context;
    private SimpleDateFormat sdfToday, sdfTomorrow, sdfYesterday;
    private SharedPreferences preferences;

    public HelperTiempos(Context context){
        this.context = context;
        sdfToday = new SimpleDateFormat(context.getString(R.string.patternToday));
        sdfTomorrow = new SimpleDateFormat(context.getString(R.string.patternTomorrow));
        sdfYesterday = new SimpleDateFormat(context.getString(R.string.patternYesterday));
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String tiempoRestante(long millisEv){
        Calendar ev = Calendar.getInstance(), now = Calendar.getInstance();
        ev.setTimeInMillis(millisEv);
        if(preferences.getBoolean("prettytime", false))
            return DateUtils.getRelativeTimeSpanString(millisEv).toString();

        if(ev.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
                && ev.get(Calendar.YEAR) == now.get(Calendar.YEAR)){
            return stringToday(ev);
        }else if(ev.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) - 1 ||
                (ev.get(Calendar.DAY_OF_YEAR) == now.getActualMaximum(Calendar.DAY_OF_YEAR) && now.get(Calendar.DAY_OF_YEAR) == 1 && ev.get(Calendar.YEAR) == now.get(Calendar.YEAR) - 1)){
            return stringYesterday(ev);
        }else if(ev.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) + 1 ||
                (now.get(Calendar.DAY_OF_YEAR) == now.getActualMaximum(Calendar.DAY_OF_YEAR) && ev.get(Calendar.DAY_OF_YEAR) == 1 && ev.get(Calendar.YEAR) == now.get(Calendar.YEAR) + 1)){
            // "Mañana a las 13:00"
            // TODO: tener en cuenta horas bajas (00:00-02:30)
            return stringTomorrow(ev);
        }else if (ev.get(Calendar.DAY_OF_YEAR) > now.get(Calendar.DAY_OF_YEAR) + 1 ||
                ev.get(Calendar.YEAR) > now.get(Calendar.YEAR)){
            // "22/02 a las 13:00"
        }else{

        }
        return DateUtils.getRelativeTimeSpanString(millisEv).toString();
    }

    private String stringToday(Calendar c){
        return sdfToday.format(c.getTime());
    }
    private String stringTomorrow(Calendar c){
        return sdfTomorrow.format(c.getTime());
    }
    private String stringYesterday(Calendar c){
        return sdfYesterday.format(c.getTime());
    }
}
