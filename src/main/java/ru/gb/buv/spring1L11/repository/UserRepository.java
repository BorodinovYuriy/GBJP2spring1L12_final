package ru.gb.buv.spring1L11.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.buv.spring1L11.entity.Roles;
import ru.gb.buv.spring1L11.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);



}
