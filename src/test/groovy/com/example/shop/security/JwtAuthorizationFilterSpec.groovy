package com.example.shop.security


import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.ProviderManager
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilterSpec extends Specification {

    def providerManager = Mock(ProviderManager)

    def jwtAuthorizationFilter = new JwtAuthorizationFilter(providerManager)

    def 'Should'() {
        given:
        def request = Mock(HttpServletRequest)
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)

        def token = null

        when:
        jwtAuthorizationFilter.doFilterInternal(request, response, chain)

        then:
        1 * request.getHeader(HttpHeaders.AUTHORIZATION) >> token
        1 * chain.doFilter(request, response)
        1 * token.startsWith("Basic ")
    }
}
