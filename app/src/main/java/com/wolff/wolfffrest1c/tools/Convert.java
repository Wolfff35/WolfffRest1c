package com.wolff.wolfffrest1c.tools;


import com.wolff.wolfffrest1c.objects.WUsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Created by wolff on 23.02.2017.
 */

public class Convert {
    public Date getDateFromString(String strDate,String strFormat){
        //2017-02-02T15:30:00
        DateFormat format = new SimpleDateFormat(strFormat, Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        System.out.println(date);
        return date;
    }
public WUsers getUserByGuid(String guid, ArrayList<WUsers> users){
    for(WUsers us:users){
        if(us.getGuid().equals(guid)){
            return us;
        }
    }
    return null;
}
    public String dateToString(Date date,String strFormat){
        //return DateFormat.getDateTimeInstance().format(date);
        if(date!=null){
        DateFormat format = new SimpleDateFormat(strFormat, Locale.ENGLISH);
        String strDate = format.format(date);
        return strDate;
        }else {
            return "";
        }


    }
    public String getStringFromInputStream(InputStream is){
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            return total.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
