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
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("OLEEEE")
                .build();
        transactionRepository.save(transaction);
        System.out.println("Transferencia guardada de forma correcta");
    }
}
