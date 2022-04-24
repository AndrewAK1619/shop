package com.example.shop.security

import com.example.shop.model.dao.User
import com.example.shop.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class SecurityServiceSpec extends Specification {

    def userService = Mock(UserService)

    def securityService = new SecurityService(userService)

    def 'Should return true when current user has access to user'() {
        // getCurrentUserEmail jest statyczną metodą, zatem możemy postarać się przygotować wszystko to
        // to ma w sobie aby metoda się wykonała. W tym celu musimy zobaczyć co jest w środku i
        // przygotować wartości. U nas securityContext, który dodamy do SecurityContextHolder
        given:
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext) // aby wstrzyknąć context dla statycznej metody
        def authentication = Mock(Authentication)
        def user = Mock(User)

        when:
        securityService.hasAccessToUser(1)

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> 'exampleDummy@example.com'
        1 * userService.getById(1) >> user
        1 * user.getEmail() >> 'exampleDummy@example.com'
        0 * _
    }
}
