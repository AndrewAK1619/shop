package com.example.shop.security

import spock.lang.Specification

import static com.example.shop.security.SecurityUtils.getCurrentUserEmail

class AuditorAwareImplSpec extends Specification {

    def auditorAwareImpl = new AuditorAwareImpl()

    def 'Should get current auditor'() {
        when:
        auditorAwareImpl.getCurrentAuditor()

        then:
        1 * getCurrentUserEmail() >> ''
        0 * _
    }
}
