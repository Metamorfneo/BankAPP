package com.grupito.springbank.repository;

import com.grupito.springbank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


//Declaramos una interfaz , esta interfaz es usada para interactuar con la base de datos para operaciones relacionadas con las transacciones.
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    //No hago nada aqui ya que cuando extendemos de JpaRepository ya nos da tod lo que necesitamos
    //Para el CRUD(Create , Read , Update , delete)
}
