package com.wolff.wolfffrest1c.xml;

import android.util.Log;

import com.wolff.wolfffrest1c.objects.WTask;
import com.wolff.wolfffrest1c.objects.WUsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wolff on 14.02.2017.
 */

public class XmlParser {
    public ArrayList<WTask> getListOfTask(String data) {
        ArrayList<WTask> tasks = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(data));
            Map fields = new HashMap<String, String>();
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if(parser.getEventType()==XmlPullParser.START_TAG){
                    //Log.e("------------",""+parser.getName()+"----- "+parser.getName().indexOf("d:"));
                    if(parser.getName().indexOf("d:")==0) {
                        String lname=parser.getName();
                        while (parser.getEventType()!=XmlPullParser.TEXT){
                            parser.next();
                        }
                        //Log.e("ADDD",""+lname+": "+parser.getText());
                        fields.put(lname,parser.getText());
                        parser.next();

                    }
                    if(parser.getName().indexOf("d:")!=0&&fields.isEmpty()==false){
                        //добавляем в список
                        String l_guid = (String) fields.get("d:Ref_Key");
                        String l_code = (String) fields.get("d:Code");
                        String l_name = (String) fields.get("d:Description");
                        String l_authorKey = (String) fields.get("d:Автор_Key");
                        String l_progKey = (String) fields.get("d:Исполнитель_Key");
                        String l_text = (String) fields.get("d:Содержание");
                        String l_ps = (String) fields.get("d:Примечание");
                        String l_isClosed = (String) fields.get("d:фЗавершена");
                        String l_dateClosed = (String) fields.get("d:ДатаЗавершения");
                        String l_isInWork = (String) fields.get("d:фПринятаВРаботу");
                        String l_dateInWork = (String) fields.get("d:ДатаПринятияВРаботу");
                        String l_dateCreate = (String) fields.get("d:ДатаСоздания");

                       // tasks.add(new WTask(l_guid,l_code,l_name,
                       //         getUserByGuid(l_authorKey),getUserByGuid(l_progKey),
                       //         l_text,l_ps,Boolean.valueOf(l_isClosed),getDateFromString(l_dateClosed),
                       //         Boolean.valueOf(l_isInWork),getDateFromString(l_dateInWork),getDateFromString(l_dateCreate)));
                       // for(Object a:fields.keySet()){
                            //Log.e("==",""+a.toString()+" - "+fields.get(a));
                       // }
                       // Log.e("","==================================================================");
                        fields.clear();
                    }

               }
                parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }


     private WUsers getUserByGuid(String guid){
        return null;
    }
 }
