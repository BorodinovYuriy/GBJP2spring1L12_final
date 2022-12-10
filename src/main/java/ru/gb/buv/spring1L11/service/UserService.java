package ru.gb.buv.spring1L11.service;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.buv.spring1L11.entity.Roles;
import ru.gb.buv.spring1L11.entity.User;
import ru.gb.buv.spring1L11.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRolesService userRolesService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @EventListener(ApplicationReadyEvent.class)
    protected void createUserDemo(){
        userRepository.save(makeUserTest(1L,"USER",
                "$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW",
                userRolesService.userRolesRepository.findAllByUser_id(1L)));//100

        userRepository.save(makeUserTest(2L,"MANAGER",
                "$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW",
                userRolesService.userRolesRepository.findAllByUser_id(2L)));//100

        userRepository.save(makeUserTest(3L,"ADMIN",
                "$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW",
                userRolesService.userRolesRepository.findAllByUser_id(3L)));//100

        userRepository.save(makeUserTest(4L,"SUPERADMIN",
                "$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW",
                userRolesService.userRolesRepository.findAllByUser_id(4L)));//100

    }
    public User makeUserTest(Long id, String username, String password,Collection<Roles> roles){
        User u = new User();
        u.setId(id);
        u.setUsername(username);
        u.setPassword(password);
        u.setRoles(roles);
        return u;
    }
}