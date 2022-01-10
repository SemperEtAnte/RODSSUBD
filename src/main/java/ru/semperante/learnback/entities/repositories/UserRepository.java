package ru.semperante.learnback.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.semperante.learnback.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("SELECT u FROM User u WHERE lower(u.login) = lower(?1) OR lower(u.email) = LOWER(?1)")
    Optional<User> findByCredentials(String login);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users u WHERE lower(u.login) = lower(?1) OR lower(u.email) = lower(?2))", nativeQuery = true)
    boolean existsByCredentials(String login, String email);


    @Query("SELECT u FROM User  u WHERE lower(u.login) = lower(?1)")
    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM User  u WHERE lower(u.email) = lower(?1)")
    Optional<User> findByEmail(String email);
}
