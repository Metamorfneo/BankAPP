package com.grupito.springbank.service.impl;

import com.grupito.springbank.dto.TransactionDto;

public interface TransactionService {
    //metodo para guardar la transaccion en la base de datos
    void saveTransaction(TransactionDto transactionDto);
}
