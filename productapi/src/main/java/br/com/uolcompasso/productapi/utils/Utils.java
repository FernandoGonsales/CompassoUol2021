package br.com.uolcompasso.productapi.utils;

public class Utils {

    public static String transformToSqlConsult(String string) {
        return "%" + string + "%";
    }

}
