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
import ru.gb.buv.spring1L11.entity.Role;
import ru.gb.buv.spring1L11.entity.User;
import ru.gb.buv.spring1L11.repository.RoleRepository;
import ru.gb.buv.spring1L11.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //************************************************************************************************
    //Ищет юзера по имени и возвращает его userdetails (Spring_security)
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        //Строка перепаковывается в строку другого объекта
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    //************************************************************************************************
    @EventListener(ApplicationReadyEvent.class)
    protected void createUserDemo(){
        userRepository.save(makeUserTest(
                1L,
                "USER",
                "$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW",
                "ROLE_USER"));//100

        userRepository.save(makeUserTest(
                2L,
                "MANAGER",
                "$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW",
                "ROLE_MANAGER"));//100

        userRepository.save(makeUserTest(
                3L,
                "ADMIN",
                "$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW",
                "ROLE_ADMIN"));//100

        userRepository.save(makeUserTest(
                4L,
                "SUPERADMIN",
                "$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW",
                "ROLE_SUPERADMIN"));//100
        List<User> users = userRepository.findAll();
        for (int i = 0; i <users.size() ; i++) {
            System.out.println(users.get(i).getUsername());
        }
    }
    public User makeUserTest(Long id, String username, String password, String stringRole){
        User u = new User();
        u.setId(id);
        u.setUsername(username);
        u.setPassword(password);
        
        Role role = new Role();
        role.setName(stringRole);
        roleRepository.save(role);
        u.setRoles(roleRepository.findByName(stringRole));
        return u;
    }

    public List<String> getAllUsernames() {
        List<User> users = userRepository.findAll();
        return users.stream().map(User::getUsername).collect(Collectors.toList());
    }
}