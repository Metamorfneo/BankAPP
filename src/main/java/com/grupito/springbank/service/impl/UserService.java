package com.grupito.springbank.service.impl;

import com.grupito.springbank.dto.BankResponse;
import com.grupito.springbank.dto.UserRequest;

public interface UserService {
  BankResponse createAccount(UserRequest userRequest);
}
