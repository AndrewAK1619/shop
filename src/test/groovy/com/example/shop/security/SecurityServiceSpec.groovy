package com.example.shop.security

import com.example.shop.model.dao.User
import com.example.shop.service.UserService
import spock.lang.Specification

import static com.example.shop.security.SecurityUtils.getCurrentUserEmail

class SecurityServiceSpec extends Specification {

    def userService = Mock(UserService)

    def securityService = new SecurityService(userService)
    
    def 'Should'() {
        given:
        def user = Mock(User)

        when:
        securityService.hasAccessToUser(1)

        then:
        1 * userService.getById(1) >> user
        1 * user.getEmail() >> 'example@example.com'
        1 * getCurrentUserEmail() >> 'example@example.com'
        0 * _
    }
}
