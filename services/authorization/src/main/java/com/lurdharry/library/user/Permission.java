package com.lurdharry.library.user;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
