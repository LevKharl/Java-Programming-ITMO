package com.example.lab8client;

import java.util.Locale;

public class CurrLocale {
    public static Locale locale;

    public static void setLocale(Locale locale){
        CurrLocale.locale = locale;
    }
    public static Locale getLocale(){
        return locale;
    }

}
