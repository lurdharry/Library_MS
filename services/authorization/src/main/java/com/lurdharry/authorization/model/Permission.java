package com.lurdharry.authorization.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "user_permissions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        }
)
public class Permission {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,nullable = false)
    private  String name;
}
