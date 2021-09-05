package com.example.shop.security;

import com.example.shop.model.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

// do logowania
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    // wstrzykujemy ręcznie, bo mamy controlera
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        // nadpisanie endpointu do logowania - domyślne "/login"
        setFilterProcessesUrl("/api/login");
    }

    // jak z controllera, pobiera dane z "/api/login"
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            // request.getInputStream() - pobiera naszego JSAONA z requestu
            LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                    loginDto.getPassword());
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    // tworzenie i przekazywanie tokena
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        // hasła nie podajemy do tokenu bo można go odczytać
        Claims claims = new DefaultClaims()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .setSubject(authResult.getName());

        // pobieramy role użytkownika i przekrztałcamy do jednego stringa - "nazwaRoli,nazwaRoli"
        // robi to aby dodać role do tokenu
        claims.put("authorities", authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));

        // sekretny klucz to suma kontrolna w tokenie
        // budujemy tokena
        String token = Jwts.builder()
                // przekazujemy dane z claims
                .setClaims(claims)
                // wskazujemy algotytm i sekretny klucz
                .signWith(SignatureAlgorithm.HS512, "sekret")
                .compact();

        // wstawienie, że zwracamy JSON'a
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // do zamieniania obiektów Javowych na Json'y i na odwrót
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("token", token));
    }
}
