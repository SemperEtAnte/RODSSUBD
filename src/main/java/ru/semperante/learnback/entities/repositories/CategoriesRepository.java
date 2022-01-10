package ru.semperante.learnback.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.semperante.learnback.entities.Categories;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories, Long>
{
    @Query(value = "SELECT EXISTS(SELECT 1 FROM categories c WHERE lower(c.name) = lower(?1))", nativeQuery = true)
    boolean existsByName(String name);

    @Query("SELECT c FROM Categories c WHERE lower(c.name) = lower(?1)")
    Optional<Categories> findByName(String name);
}
