package com.example.shop.service.impl

import com.example.shop.model.dao.Role
import com.example.shop.model.dao.User
import com.example.shop.repository.RoleRepository
import com.example.shop.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import javax.persistence.EntityNotFoundException
import java.time.LocalDate

class UserServiceImplSpec extends Specification {

    def userRepository = Mock(UserRepository)
    def passwordEncoder = Mock(PasswordEncoder)
    def roleRepository = Mock(RoleRepository)
    def mailService = Mock(MailServiceImpl)

    def userServiceImpl = new UserServiceImpl(userRepository,
            passwordEncoder, roleRepository, mailService)

    def 'Should create new user'() {
        given:
        def user = Mock(User)

        when:
        userServiceImpl.create(user)

        then:
        1 * user.getPassword() >> 'Example'
        1 * passwordEncoder.encode('Example') >> 'adsaBBUJKSafa34'
        1 * user.setPassword('adsaBBUJKSafa34')
        1 * roleRepository.findByName('ROLE_USER') >> Optional.of(new Role(name: 'ROLE_USER'))
        1 * user.setRoles([new Role(name: 'ROLE_USER')] as Set<Role>)
        1 * user.setActivatedToken(_ as String)
        1 * userRepository.save(user)
        1 * user.getFirstName()
        1 * user.getEmail() >> 'exampleDummy@example.com'
        1 * mailService.sendMail('exampleDummy@example.com', 'createUser', _, null, null)
        0 * _
    }

    def 'Should update user'() {
        given:
        def user = Mock(User)

        when:
        userServiceImpl.update(1, user)

        then:
        1 * userRepository.getById(1) >> user
        1 * user.getFirstName() >> 'DummyFirstName'
        1 * user.setFirstName('DummyFirstName')
        1 * user.getLastName() >> 'DummyLastName'
        1 * user.setLastName('DummyLastName')
        1 * user.getBirthDate() >> LocalDate.of(2010, 5, 5)
        1 * user.setBirthDate(LocalDate.of(2010, 5, 5))
        1 * user.getEmail() >> 'exampleDummy@example.com'
        1 * user.setEmail('exampleDummy@example.com')
        0 * _
    }

    def 'Should get user by id'() {
        when:
        userServiceImpl.getById(1)

        then:
        1 * userRepository.getById(1)
        0 * _
    }

    def 'Should get current user'() {
        given:
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext) // aby wstrzyknąć context dla statycznej metody
        def authentication = Mock(Authentication)

        when:
        userServiceImpl.getCurrentUser()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> 'exampleDummy@example.com'
        1 * userRepository.findByEmail('exampleDummy@example.com') >> Optional.of(new User())
        0 * _
    }

    def 'Should not get current user and thrown EntityNotFoundException'() {
        // getCurrentUserEmail jest statyczną metodą, zatem możemy postarać się przygotować wszystko to
        // to ma w sobie aby metoda się wykonała. W tym celu musimy zobaczyć co jest w środku i
        // przygotować wartości. U nas securityContext, który dodamy do SecurityContextHolder
        given:
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext) // aby wstrzyknąć context dla statycznej metody
        def authentication = Mock(Authentication)

        when:
        userServiceImpl.getCurrentUser()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> 'exampleDummy@example.com'
        1 * userRepository.findByEmail('exampleDummy@example.com') >> Optional.empty()
        0 * _
        def exception = thrown EntityNotFoundException
        exception.message == 'User not logged'
    }

    def 'Should delete by id'() {
        when:
        userServiceImpl.deleteById(1)

        then:
        1 * userRepository.deleteById(1)
        0 * _
    }

    def 'Should get page with users'() {
        given:
        def pageable = Mock(Pageable)

        when:
        userServiceImpl.getPage(pageable)

        then:
        1 * userRepository.findAll(pageable)
        0 * _
    }
}
