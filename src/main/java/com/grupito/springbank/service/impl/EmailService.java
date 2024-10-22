package com.grupito.springbank.service.impl;

import com.grupito.springbank.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
