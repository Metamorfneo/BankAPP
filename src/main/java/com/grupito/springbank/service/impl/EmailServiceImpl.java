package com.grupito.springbank.service.impl;


import com.grupito.springbank.dto.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    //Metodo para enviar Emails simples
    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try {
            //Creamos el objeto para enviar emails simples
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            //Seteamos la direccion del que manda el email
            mailMessage.setFrom(senderEmail);

            //Seteamos el email de la persona a la que se lo queremos mandar
            mailMessage.setTo(emailDetails.getRecipient());

            //Seteamos el contedido del mensaje
            mailMessage.setText(emailDetails.getMessageBody());

            //Seteamos el asunto del email
            mailMessage.setSubject(emailDetails.getSubject());

            //Mandamos el email utiliznado JavaMailSender
            javaMailSender.send(mailMessage);
            System.out.println("El correo se ha enviado de forma correcta");
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithAttachement(EmailDetails emailDetails) {
        //Utilizamos Mime , el cual nos deja enviar Mails con Adjuntos.
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            //Ponemos True , para que nos deje adjuntar archivos
            mimeMessageHelper = new MimeMessageHelper(mimeMessage , true);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setText(emailDetails.getMessageBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            //Lo unico que cambia es cuando adjuntamos un archivo
            //Cargamos el archivo a adjuntar utilizando el path del archivo
            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachement()));

            //Añadimos el archivo al mail
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            javaMailSender.send(mimeMessage);
            log.info(file.getFilename() + " Se ha enviado correctamente al correo " + emailDetails.getRecipient());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
