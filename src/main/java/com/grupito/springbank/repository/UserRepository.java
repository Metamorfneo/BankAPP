package com.grupito.springbank.repository;

import com.grupito.springbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


//Esta interfaz se utiliza para las operaciones en la base de datos que tengan que ver con la identida del usuario
public interface UserRepository extends JpaRepository<User , Long> {

        //Chekeamos si un usuario existe en la base de datos con el email dado
        //Si existe devuelve true , si no , false
        Boolean existsByEmail(String email);

        //Busca un usuario por su email
        //Ponemos optional por que el usuario puede ser encontrado o no por el email
        Optional<User> findByEmail(String email);

        //Chekeamos si el usuario con el numero de cuenta dado existe.
        Boolean existsByAccountNumber(String accountNumber);


        //Buscamos al usuario por su numero de cuenta
        User findByAccountNumber(String accountNumber);


}
