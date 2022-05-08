package com.example.shop.security


import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilterSpec extends Specification {

    def authManager = Mock(AuthenticationManager)

    JwtAuthorizationFilter jwtAuthorizationFilter = Spy(JwtAuthorizationFilter, constructorArgs: [authManager]) as JwtAuthorizationFilter

    def 'Should return when token is incorrect'() {
        given:
        def request = Mock(HttpServletRequest)
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)

        when:
        jwtAuthorizationFilter.doFilterInternal(request, response, chain)

        then:
        1 * jwtAuthorizationFilter.doFilterInternal(request, response, chain)
        1 * request.getHeader(HttpHeaders.AUTHORIZATION) >> token
        1 * chain.doFilter(request, response)
        0 * _

        where:
        token << [null, 'dsda']
    }

//    def 'Should return when token is correct'() {
//        given:
//        def request = Mock(HttpServletRequest)
//        def response = Mock(HttpServletResponse)
//        def chain = Mock(FilterChain)
//
//        def token = "Basic sdasfds"
//
//        when:
//        jwtAuthorizationFilter.doFilterInternal(request, response, chain)
//
//        then:
//        1 * jwtAuthorizationFilter.doFilterInternal(request, response, chain)
//        1 * request.getHeader(HttpHeaders.AUTHORIZATION) >> token
//        1 * jwtAuthorizationFilter.super$3$doFilterInternal(request, response, chain) >> {}
//        0 * _
//
////        where:
////        token << [null, 'dsda']
//    }
}
