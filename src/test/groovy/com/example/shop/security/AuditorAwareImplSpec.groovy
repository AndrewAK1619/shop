package com.example.shop.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class AuditorAwareImplSpec extends Specification {

    def auditorAwareImpl = new AuditorAwareImpl()

    def 'Should get current auditor'() {
        // getCurrentUserEmail jest statyczną metodą, zatem możemy postarać się przygotować wszystko to
        // to ma w sobie aby metoda się wykonała. W tym celu musimy zobaczyć co jest w środku i
        // przygotować wartości. U nas securityContext, który dodamy do SecurityContextHolder
        given:
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext) // aby wstrzyknąć context dla statycznej metody
        def authentication = Mock(Authentication)

        when:
        def result = auditorAwareImpl.getCurrentAuditor()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> 'exampleDummy@example.com'
        0 * _
        result == Optional.of("exampleDummy@example.com")
    }
}
