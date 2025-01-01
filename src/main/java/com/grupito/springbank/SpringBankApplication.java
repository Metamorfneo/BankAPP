package com.grupito.springbank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "BankAppSam",
                description = "Trabajo de final de grado",
                version = "0.9",
                contact = @Contact(
                        name = "Samuel Blazquez Diaz",
                        email = "sblazquez2002@gmail.com",
                        url = "https://github.com/Metamorfneo/BankAPP"
                ),
                license = @License(
                        name = "Samuel Blazquez diaz",
                        url = "https://github.com/Metamorfneo/BankAPP"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "TFG Samuel Blazquez",
                url = "https://github.com/Metamorfneo/BankAPP"
        )

)
public class SpringBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBankApplication.class, args);
    }

}
