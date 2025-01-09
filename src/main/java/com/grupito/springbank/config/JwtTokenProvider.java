package com.grupito.springbank.config;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenProvider {

    //Inyectamos el valor de app.jwt-secret
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    //Inyectamos el valor de app.jwt-expiration(el tiempo de duracion del token en milisegundos)
    @Value("${app.jwt-expiration}")
    private long jwtExpirationDate;

    //Metodo para generar JWT para un usuario con autenticacion
    public String generateToken(Authentication authentication) {

        //Recogemos el nombre de usuario del usuario autenticado
        String username = authentication.getName();

        //Recogemos la fecha actual
        Date currentDate = new Date();

        //Calculamos la fehca de expiracion añadiendo la duracion al tiempo actual
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);

        //Construimos y devolvemos el JWT usando el nombre de usuario , el Issue datee , la fehca de expiracion y la key de firma.
        return Jwts.builder()
                .setSubject(username)//Seteamos el nombre de usuario como el usuario de token(Para identidicar)
                .setIssuedAt(currentDate)//
                .setExpiration(expirationDate)//Seteamos el tiempo de expiracion
                .signWith(key())// firmamos el token utilizando la clave privada
                .compact();// Ponemos el JWT en la forma final((String)
    }

    //Clase de apoyo para crear una clave criptografica para firmar el JWT.
    private Key key(){
        byte[] bytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(bytes);
    }


    //Sustraemos el nombre de usuario del JWT dado
    public String getUsername(String token) {

        //Parseamos el JWT , validamos con la clave y extraemos la info
        Claims claims = Jwts.parser()
                .setSigningKey(key()) //Utilizamos la clave de firma para validar el token
                .build()
                .parseClaimsJws(token) //Parseamos el token y sustraemos el JWS(JSON web signature)
                .getBody(); //Sustraemos la info del token parseado.

        return claims.getSubject();
    }


    //Validamos el token para ver que esta correctamente firmado y no ha expirado
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException | IllegalArgumentException | SecurityException | MalformedJwtException e) {
            throw new RuntimeException(e);
        }
    }
}
