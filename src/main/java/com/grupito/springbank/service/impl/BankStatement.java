package com.grupito.springbank.service.impl;


import com.grupito.springbank.dto.EmailDetails;
import com.grupito.springbank.entity.Transaction;
import com.grupito.springbank.entity.User;
import com.grupito.springbank.repository.TransactionRepository;
import com.grupito.springbank.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j

public class BankStatement {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    //Constante de donde se guardan los PDF generados
    private static final String FILE="~/Desktop/PdfGenerados";//Buscar como y donde se guardan los documentos


    //Poder hacer una lista de los movimientos dada una fecha y un numero de cuenta
    //generar un PDF con los movimientos
    //enviar el PDF por email


    //Metodo para generar info del banco, crear un PDF y enviarlo via email
    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
        LocalDate start = LocalDate.parse(startDate , DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate , DateTimeFormatter.ISO_DATE);

        //Filtramos la transaccion por numero de cuenta y fecha
        List<Transaction> transactionList = transactionRepository.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isEqual(start)).filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();

        //Recogemos info del ususario por el numero de cuenta
        User user = userRepository.findByAccountNumber(accountNumber);

        //Concatenamos los detalles del usuario para crear el nombre completo
        String customerName = user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName();

        //Seteamos el tamaño del fichero a A4
        Rectangle statementSize = PageSize.A4;
        Document document = new Document(statementSize);
        log.info("Seteando tamano del documento");

        //Preparamos un OS para escribrir el PDF en path concreto
        OutputStream outputStream = new FileOutputStream(FILE);

        //Asociamos el documento con el OS
        PdfWriter.getInstance(document, outputStream);

        //Abrimos el documento
        document.open();


        //Creamos la tabla para la informacion del banco de una columna
        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("TFG de Samuel"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.GREEN);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("21, Avenida de la caza , Alcorcon"));
        bankAddress.setBorder(0);
        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);


        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("Start date: " + startDate));
        customerInfo.setBorder(0);
        PdfPCell statement = new PdfPCell(new Phrase("Statement of account "));
        statement.setBorder(0);
        PdfPCell stopDate = new PdfPCell(new Phrase("End date: " + endDate));
        stopDate.setBorder(0);
        PdfPCell name = new PdfPCell(new Phrase("Customer name: " + customerName));
        name.setBorder(0);
        PdfPCell space = new PdfPCell();
        space.setBorder(0);
        PdfPCell address = new PdfPCell(new Phrase("Address: " + user.getAddress()));
        address.setBorder(0);


        PdfPTable transactionTable = new PdfPTable(4);
        PdfPCell date = new PdfPCell(new Phrase("Date"));
        date.setBackgroundColor(BaseColor.GREEN);
        date.setBorder(0);
        PdfPCell transactionType = new PdfPCell(new Phrase("Transaction type"));
        transactionType.setBackgroundColor(BaseColor.GREEN);
        transactionType.setBorder(0);
        PdfPCell TransactionAmount = new PdfPCell(new Phrase("Amount"));
        TransactionAmount.setBackgroundColor(BaseColor.GREEN);
        TransactionAmount.setBorder(0);
        PdfPCell status = new PdfPCell(new Phrase("Status"));
        status.setBackgroundColor(BaseColor.GREEN);
        status.setBorder(0);

        transactionTable.addCell(date);
        transactionTable.addCell(transactionType);
        transactionTable.addCell(TransactionAmount);
        transactionTable.addCell(status);

        transactionList.forEach(transaction -> {
            transactionTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
            transactionTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionTable.addCell(new Phrase(transaction.getAmount().toString()));
            transactionTable.addCell(new Phrase(transaction.getStatus()));
        });


        //Añadimos la info al documento
        statementInfo.addCell(customerInfo);
        statementInfo.addCell(statement);
        statementInfo.addCell(endDate);
        statementInfo.addCell(name);
        statementInfo.addCell(space);
        statementInfo.addCell(address);


        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionTable);

        //Cerramos el documento
        document.close();

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Info de la cuenta")
                .messageBody("Aqui tienes tu info bro")
                .attachement(FILE)
                .build();

        emailService.sendEmailWithAttachement(emailDetails);

        return transactionList;
    }



}
