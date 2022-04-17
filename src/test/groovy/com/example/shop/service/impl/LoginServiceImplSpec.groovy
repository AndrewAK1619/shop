package com.example.shop.service.impl

import com.example.shop.model.dto.LoginDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import spock.lang.Specification

class LoginServiceImplSpec extends Specification {

    def authenticationManager = Mock(AuthenticationManager)

    def loginServiceImpl = new LoginServiceImpl(authenticationManager)

    def 'Should login user'() {
        given:
        def loginDto = Mock(LoginDto)
        var userNamePassword = new UsernamePasswordAuthenticationToken(
                'example@example.com',
                'dummyPassword'
        )
        def authentication = Mock(Authentication)
        def grantedAuthority = Mock(GrantedAuthority)

        when:
        loginServiceImpl.login(loginDto)

        then:
        1 * loginDto.getEmail() >> 'example@example.com'
        1 * loginDto.getPassword() >> 'dummyPassword'
        1 * authenticationManager.authenticate(userNamePassword) >> authentication
        1 * authentication.getName() >> 'DummyName'
        1 * authentication.getAuthorities() >> [grantedAuthority]
        1 * grantedAuthority.getAuthority() >> 'Authority'
        0 * _
    }
}
