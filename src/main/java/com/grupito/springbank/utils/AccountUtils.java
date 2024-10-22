package com.grupito.springbank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACOUNT_EXIST_CODE= "001";
    public static final  String ACCOUNT_EXISTS_MESSAGE = "La cuenta ya existe";

    public static final String ACCOUNT_CREATION_SUCCES ="002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Se ha creado la cuenta de forma correcta";



    public static String generateAccountNumber() {
        //2024 + seisNumeroAleatorios

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        //Generamos un numero aleatorio entre el maximo y el minimo

        int randNumber =(int) Math.floor(Math.random() * (max - min + 1) + min);

        //Convertimos los numeros en String para poder manejarlos mejos y depues concatenarlos

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();

       return accountNumber.append(year).append(randomNumber).toString();
    }
}