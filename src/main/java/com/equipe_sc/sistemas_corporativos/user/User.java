package com.equipe_sc.sistemas_corporativos.user;

import com.equipe_sc.sistemas_corporativos.employee.Candidate;
import com.equipe_sc.sistemas_corporativos.role.Role;
import com.equipe_sc.sistemas_corporativos.role.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username")
        }
)
public class User implements UserDetails {

    //region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", length = 13, nullable = false)
    private String phone;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Setter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    //endregion

    //region Relationships

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Candidate candidate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    //endregion

    //region Getter and Setters

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (username.length() > 255) {
            throw new IllegalArgumentException("Username cannot be longer than 255 characters");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }

        if (!username.contains("@")) throw new IllegalArgumentException("Username must be an email and contain @");

        this.username = username.toLowerCase();
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        this.password = password;
    }

    public void setPhone(String phone) {
        if (phone == null || (phone.length() != 12 && phone.length() != 13)) {
            throw new IllegalArgumentException("Phone number must be 13/12 digits long");
        }
        if (!phone.matches("\\d+")) {
            throw new IllegalArgumentException("Phone must contain only digits.");
        }
        this.phone = phone;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name cannot be longer than 100 characters");
        }
        if (!name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s'-]+$")) {
            throw new IllegalArgumentException("Name can only contain letters, spaces, apostrophes, and hyphens");
        }
        this.name = name.toUpperCase();
    }

    public void setRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return;
        }
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (this.roles == null || this.roles.isEmpty()) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void addAllRoles(Set<Role> roles) {
        if (this.roles == null || this.roles.isEmpty()) {
            this.roles = new HashSet<>();
        }
        this.roles.addAll(roles);
    }

    //endregion

    //region UserDetails Implementation

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.roles == null) {
            this.roles = new HashSet<>();
        }
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    //endregion

    // region Other Methods

    public boolean isAdmin() {
        if (this.roles == null || this.roles.isEmpty()) {
            return false;
        }
        return this.roles.stream()
                .anyMatch(r -> r.getRoleName().equals(RoleName.ROLE_ADMIN));
    }

    public boolean isCandidate() {
        if (this.roles == null || this.roles.isEmpty()) {
            return false;
        }
        return this.roles.stream()
                .anyMatch(r -> r.getRoleName().equals(RoleName.ROLE_CANDIDATE));
    }

    public boolean isEmployee() {
        if (this.roles == null || this.roles.isEmpty()) {
            return false;
        }
        return this.roles.stream()
                .anyMatch(r -> r.getRoleName().equals(RoleName.ROLE_EMPLOYEE));
    }

    // endregion

}
