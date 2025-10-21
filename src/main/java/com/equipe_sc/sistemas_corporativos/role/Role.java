package com.equipe_sc.sistemas_corporativos.role;

import com.equipe_sc.sistemas_corporativos.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    public Role (RoleName roleName) {
        this.setName(roleName);
    }

    public RoleName getRoleName() {
        return this.name;
    }

    public String getName() {
        return this.name.name();
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users;

    public boolean isAdmin() {
        return this.name == RoleName.ROLE_ADMIN;
    }

    public boolean isEmployee() {
        return this.name == RoleName.ROLE_EMPLOYEE;
    }

    public boolean isCandidate() {
        return this.name == RoleName.ROLE_CANDIDATE;
    }

}
