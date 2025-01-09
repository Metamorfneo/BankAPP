package com.grupito.springbank.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

//Esta clase es parte del framework de seguridad de springBoot , usado para manejar accesos no autorizados.

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //Este metodo se utiliza cuando hay un acceso no autorizado en la app
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //Mandamos una respuesta HTTP(con codigo 401) para indicar que no esta autorizado.
        //GetMessage es para recibir mas info acerca del error de autenticacion
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
