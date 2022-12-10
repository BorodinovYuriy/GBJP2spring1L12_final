package ru.gb.buv.spring1L11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.buv.spring1L11.entity.Roles;
import ru.gb.buv.spring1L11.repository.RoleRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    protected void addRoleToRole(){
        Roles rolesUSER = new Roles();
        rolesUSER.setId(1L);
        rolesUSER.setName("ROLE_USER");             //1

        Roles rolesMANAGER = new Roles();
        rolesMANAGER.setId(2L);
        rolesMANAGER.setName("ROLE_MANAGER");       //2

        Roles rolesADMIN = new Roles();
        rolesADMIN.setId(3L);
        rolesADMIN.setName("ROLE_ADMIN");           //3

        Roles rolesSUPERADMIN = new Roles();
        rolesSUPERADMIN.setId(4L);
        rolesSUPERADMIN.setName("ROLE_SUPERADMIN"); //4

        roleRepository.save(rolesUSER);
        roleRepository.save(rolesMANAGER);
        roleRepository.save(rolesADMIN);
        roleRepository.save(rolesSUPERADMIN);

    }


}
