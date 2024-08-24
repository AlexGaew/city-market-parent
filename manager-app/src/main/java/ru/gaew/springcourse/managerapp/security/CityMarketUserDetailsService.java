package ru.gaew.springcourse.managerapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gaew.springcourse.managerapp.entity.Authority;
import ru.gaew.springcourse.managerapp.repository.CityMarketUserReposittory;

@Service
@RequiredArgsConstructor
public class CityMarketUserDetailsService implements UserDetailsService {


    private final CityMarketUserReposittory cityMarketUserReposittory;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.cityMarketUserReposittory.findByuserName(username)
                .map(user -> User.builder()
                        .username(user.getUserName())
                        .password(user.getPassword())
                        .authorities(user.getAuthorities()
                                .stream()
                                .map(Authority::getAuthority).map(SimpleGrantedAuthority::new).toList())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
    }
}
