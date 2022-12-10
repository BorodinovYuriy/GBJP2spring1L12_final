package ru.gb.buv.spring1L11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.buv.spring1L11.entity.Roles;
import ru.gb.buv.spring1L11.entity.User_roles;
import ru.gb.buv.spring1L11.repository.UserRepository;
import ru.gb.buv.spring1L11.repository.UserRolesRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRolesService {
    UserRolesRepository userRolesRepository;


    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    protected void addRoleDependencyTest(){
        userRolesRepository.save(makeRole(1L,1L));
        userRolesRepository.save(makeRole(2L,2L));
        userRolesRepository.save(makeRole(3L,3L));
        userRolesRepository.save(makeRole(4L,4L));
    }
    private User_roles makeRole(Long user_id, Long role_id){
        User_roles ur = new User_roles();
        ur.setUser_id(user_id);
        ur.setRole_id(role_id);
        return ur;
    }
    private List<Roles> rolesCollectionReturnerByUserID(Long user_id){
        return userRolesRepository.findAllByUser_id(user_id);
    }
}
