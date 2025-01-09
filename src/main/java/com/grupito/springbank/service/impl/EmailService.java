package com.grupito.springbank.service.impl;

import com.grupito.springbank.dto.EmailDetails;

public interface EmailService {

    //Meotodo para enviar una alerta de Email(Sin nada adjunto)
    //Acepta lo normal (Recipient , subject and message body)
    void sendEmailAlert(EmailDetails emailDetails);

    //Metodo para enviar una alerta con algo adjunto
    //Acepta lo mismo que el normal pero pudiendo adjuntar un archivo
    void sendEmailWithAttachement(EmailDetails emailDetails);
}
