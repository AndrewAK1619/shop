package com.example.shop.security;

import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

// 1 do implementacji
// dostarczać będzie usera z bazy danych w postaci usera z security

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // new User w metodzie loadUserByUsername to obiekt z security.
    // pobrany z importu:
    // import org.springframework.security.core.userdetails.User;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // pobieramy naszego usera z bazy danych za pomoca maila
        return userRepository.findUserByEmail(email)
                // - mapujemy naszego usera na tego z security
                .map(user -> new User(user.getEmail(), user.getPassword(), user.getRoles().stream()
                        // - mapujemy wszystkie role naszego Usera
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        // - wszystkie role do seta
                        .collect(Collectors.toSet())))
                // - zwracamy Usera jeżeli isnieje, jak nie to rzucamy wyjątek
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
