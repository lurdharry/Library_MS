package com.lurdharry.authorization.user;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
        name = "user_roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        }
)
public class RoleEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,unique = true)
    private Role name;


    @ManyToMany
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
