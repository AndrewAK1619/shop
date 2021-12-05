package com.example.shop.service.impl;

import com.example.shop.model.dao.User;
import com.example.shop.repository.RoleRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;

import static com.example.shop.security.SecurityUtils.getCurrentUserEmail;

@Service
@RequiredArgsConstructor // tylko konstruktor dla finalnych zmiennych
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // koduje hasło
        // każda rola wielkimi literami i każda ma prefix - 'ROLE_' u nas ROLE_USER
        // Collections.singleton(role) - tworzy nam immutable set, są też np. singletonList / singletonMap
        roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Collections.singleton(role)));
        return userRepository.save(user);
        // save robi duzo sprawdzen jezeli obiekt ma id, jezeli ma
        // to robi select i jezeli obiekt istnieje to robi update
        // jezeli nie to insert
    }

    @Override
    @Transactional
    // jezeli pobierzerz obiekt z bazy danych to robi update jezeli obiekt zostanie zmieniony
    // jezeli nie zostanie to nie robi, robi to po returnie
    public User update(Long id, User user) {
        var userDb = getById(id);
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setBirthDate(user.getBirthDate());
        userDb.setEmail(user.getEmail());
        return userDb;
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
        // jezeli nie znajdzie user po id wyrzuca entityNotFoundExeption
        // jak znajdzie to zwraca
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not logged"));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        // robi select napierw, jesli nie znadzie wywala exeption
        // jezeli znajdzie robi delete
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
