package ru.gb.buv.spring1L11.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.buv.spring1L11.entity.Roles;

@Repository
public interface RoleRepository extends CrudRepository<Roles, Long> {
}
