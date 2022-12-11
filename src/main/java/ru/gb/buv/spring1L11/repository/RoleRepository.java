package ru.gb.buv.spring1L11.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.buv.spring1L11.entity.Role;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findById(Long id);

    Collection<Role> findByName(String stringRole);
}
