package ru.gb.buv.spring1L11.entity;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "users_roles")
public class User_roles {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "user_id")
        private Long user_id;

        @Column(name = "role_id")
        private Long role_id;


}
