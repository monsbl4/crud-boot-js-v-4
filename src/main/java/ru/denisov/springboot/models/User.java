package ru.denisov.springboot.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 30)
    private String name;

    @Column (name = "last_name", length = 60)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 60)
    @Email(message = "Неправильный формат")
    private String email;

    @Column(name = "age")
    private int age;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "username",nullable = false)
    @Size(min = 2, max = 15, message = "введите логин")
    private String username;

    @Column(name = "password")
    private String password;

    public User() {
    }

    public User( String name, String lastName, String email, int age, String username, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public User(String name, String lastName, String email, int age, String username, String password, Set <Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String name, String lastName, String email, int age, String password, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRolesUsers() {
        return roles.toString().replaceAll("^\\[|\\]$", "");
    }

}
