package com.example.shop.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

// auditing - tworzenie historii danych, informacje jakie zostały dokonane operacje (Insert, Update, Delete)

@Data   // get, set, equals, hashcode, to string, constr wielo arg dla finalnych zmiennych
@Entity
@Builder
@Audited // doda nam tabelkę audytową
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) // doda auditing dla tej encji
public class User extends Auditable {

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
    private String activatedToken;
    @ManyToMany
    // name = "user_role" - zmienia nazwę tabeli pośredniczącej, bo inaczej by było user_roles
    // inverseJoinColumns = @JoinColumn(name = "role_id") - zmienia nazwę kolumny która ma łączyć się z tabelką role
    // inaczej by było roles_id
    @JoinTable(name = "user_role", inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
