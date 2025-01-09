package com.grupito.springbank.controller;


import com.grupito.springbank.entity.Transaction;
import com.grupito.springbank.service.impl.BankStatement;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

//Ponemos RestController para poder utilizar esta clase como Bean y que pueda manejar las request HTTp
@RestController

//Especificamos los ENDPOINT en este Controller
@RequestMapping( "/bankStatement")

//IMPORTANTE , con esta inyeccion creamos AUTOMATICAMENTE el construcctor con los argumentos requeridos
//para todas las clases marcadas como final o sin inicializar
@AllArgsConstructor
public class TransactionController {


    private BankStatement bankStatement;

    //Utilizamos GetMapping para que el usuario pueda recibir a traves de PostMan la info de este EndPoint
    @GetMapping
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber, @RequestParam String startDate, @RequestParam String endDate) throws DocumentException, FileNotFoundException {


        //Retornamos una lista de las transaciones entre unas fechas concretas  con el numero de cuenta dado
        return bankStatement.generateStatement(accountNumber, startDate, endDate);

    }

}
