package com.example.shop.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// auditing - tworzenie historii danych, informacje jakie zostały dokonane operacje (Insert, Update, Delete)

@Data   // get, set, equals, hashcode, to string, constr wielo arg dla finalnych zmiennych
@Entity
@Builder
@Audited // doda nam tabelkę audytową
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) // doda auditing
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @Column(unique = true)
    private String email;
    @NotAudited // nie dodawać do audytowych tabelek
    private String password;
    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
}
