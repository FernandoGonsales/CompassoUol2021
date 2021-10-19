package br.com.uolcompasso.productapi.utils;

import java.util.Locale;

public class Utils {

    public static String transformToSqlConsult(String string) {
        return "%" + string.toLowerCase(Locale.ROOT) + "%";
    }

}
