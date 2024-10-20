package com.grupito.springbank.service.impl;

import com.grupito.springbank.dto.BankResponse;
import com.grupito.springbank.dto.UserRequest;
import com.grupito.springbank.entity.User;

public class UserServiceImpl implements UserService {

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        //Guardamos un nuevo usuario en la base de datos junto con su cuenta
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .Country(userRequest.getCountry())
                .accountNumber()
                .build();
    }
}
