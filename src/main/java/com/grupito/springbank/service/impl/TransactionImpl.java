package com.grupito.springbank.service.impl;

import com.grupito.springbank.dto.TransactionDto;
import com.grupito.springbank.entity.Transaction;
import com.grupito.springbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionImpl implements TransactionService{


    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        //Mapeamos los datos de la DTO de transaction para crear una nueva entity de transaction
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType()) //Seteamos el tipo de transaccion(Credito , debito...)
                .accountNumber(transactionDto.getAccountNumber()) //Seteamos el numero de cuenta asociado a la transaccion
                .amount(transactionDto.getAmount()) //Seteamos la cantidad
                .status("OLEEEE") //Seteamos un status por defecto a la transaccion
                .build();

        //Guardamos la transaccion en la base de datos(Esto me di cuenta tarde , antes no la guardaba y me estaba volviendo loco)
        transactionRepository.save(transaction);
        System.out.println("Transferencia guardada de forma correcta");
    }
}
