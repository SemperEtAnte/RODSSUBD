package ru.semperante.learnback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails
{
    private static final List<GrantedAuthority> authorites = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String login;
    private String email;
    private String password;
    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;


    //Hibernate требует пустого конструктора
    public User()
    {
    }

    /**
     * Конструктор для создания нового пользователя
     *
     * @param login    логин
     * @param email    почта
     * @param password пароль
     */
    public User(String login, String email, String password)
    {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public Long getId()
    {
        return id;
    }

    public String getLogin()
    {
        return login;
    }

    public String getEmail()
    {
        return email;
    }

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    public Timestamp getUpdatedAt()
    {
        return updatedAt;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorites;
    }


    @Override
    public String getUsername()
    {
        return login;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
