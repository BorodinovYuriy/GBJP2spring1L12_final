package ru.gb.buv.spring1L11.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.buv.spring1L11.entity.Roles;
import ru.gb.buv.spring1L11.entity.User_roles;

import javax.management.relation.Role;
import java.util.List;

@Repository
public interface UserRolesRepository extends CrudRepository<User_roles,Long> {

    List<Roles> findAllByUser_id(Long user_id);
}
