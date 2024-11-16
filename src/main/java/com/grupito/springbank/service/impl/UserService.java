package com.grupito.springbank.service.impl;

import com.grupito.springbank.dto.BankResponse;
import com.grupito.springbank.dto.CreditDebitRequest;
import com.grupito.springbank.dto.EnquiryRequest;
import com.grupito.springbank.dto.UserRequest;

public interface UserService {
  BankResponse createAccount(UserRequest userRequest);
  BankResponse balanceEnquiry(EnquiryRequest request);
  String nameEnquiry(EnquiryRequest request);
  BankResponse creditAccount(CreditDebitRequest request);
  BankResponse debitAccount(CreditDebitRequest request);
}
