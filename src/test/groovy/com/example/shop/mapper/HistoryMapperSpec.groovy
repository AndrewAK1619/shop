package com.example.shop.mapper

import com.example.shop.model.dao.Product
import com.example.shop.model.dao.Role
import com.example.shop.model.dao.User
import org.hibernate.envers.DefaultRevisionEntity
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata
import org.springframework.data.history.Revision
import org.springframework.data.history.RevisionMetadata
import spock.lang.Specification

import java.time.LocalDate

class HistoryMapperSpec extends Specification {

    def historyMapperImpl = new HistoryMapperImpl()

    def 'Should mapping Revision to UserDto'() {
        given:
        def user = User.builder()
                .id(1)
                .firstName('DummyFirstName')
                .lastName('DummyLastName')
                .birthDate(LocalDate.of(2011, 5, 5))
                .email('example@example.com')
                .password('example')
                .activatedToken('gdsa%d6s7afhakDsadf')
                .roles(['ROLE_ADMIN'] as Set<Role>)
                .build()
        def entity = new DefaultRevisionEntity()
        entity.setId(3)
        def metadata = new DefaultRevisionMetadata(entity, RevisionMetadata.RevisionType.INSERT)
        def revision = Revision<Integer, User>.of(metadata, user)

        when:
        def result = historyMapperImpl.revisionToUserDto(revision)

        then:
        result.id == revision.entity.id
        result.firstName == revision.entity.firstName
        result.lastName == revision.entity.lastName
        result.birthDate == revision.entity.birthDate
        result.email == revision.entity.email
        result.password == null
        result.confirmPassword == null
        result.roles == null
        result.revisionNumber == revision.getRequiredRevisionNumber()
        result.revisionType == RevisionMetadata.RevisionType.INSERT
    }

    def 'Should mapping Revision to ProductDto'() {
        given:
        def product = Product.builder()
                .id(1)
                .name('DummyName')
                .serialNumber(123433)
                .quantity(2)
                .price(434.43)
                .description('DummyDescription')
                .path('/path')
                .build()
        def entity = new DefaultRevisionEntity()
        entity.setId(3)
        def metadata = new DefaultRevisionMetadata(entity, RevisionMetadata.RevisionType.INSERT)
        def revision = Revision<Integer, Product>.of(metadata, product)

        when:
        def result = historyMapperImpl.revisionToProductDto(revision)

        then:
        result.id == revision.entity.id
        result.name == revision.entity.name
        result.serialNumber == revision.entity.serialNumber
        result.quantity == revision.entity.quantity
        result.price == revision.entity.price
        result.description == revision.entity.description
        result.revisionNumber == revision.getRequiredRevisionNumber()
        result.path == null
        result.revisionType == RevisionMetadata.RevisionType.INSERT
    }
}
