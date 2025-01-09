package com.grupito.springbank.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Esta clase actua de filtro para cada request HTTP para validar el token JWT y autenticar el usuario.


@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    //Clase que utilizo para manejar los tokens dado y extraer la informacion del usuario del token
    private JwtTokenProvider jwtTokenProvider;

    //Clase que utilizo para cargar los datos del usuario de la base de dato , basandome en el nombre de usuario del token.
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        //Sustraemos el token de la request
        String token = getTokenFromRequest(request);

        //Checkeamos si el token existe y es valido
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            //Recogemos el username de del token
            String username = jwtTokenProvider.getUsername(token);
            //Cargamos los detalles del usuario(Rol , credenciales) usando el nombre de usuario.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //Creamos un token de autenticacion usando los detalles del usuario
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //Guardamos el objeto de autenticacion en el securityContext para que no se pierda en el LifeCycle(Esto es parecido a los threads y
            //El lifeCycle de android
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //Pasamos la request y la response poor el filtro.
        filterChain.doFilter(request, response);


    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);

        }
        return null;
    }
}
