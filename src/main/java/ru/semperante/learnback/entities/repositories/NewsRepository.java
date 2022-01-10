package ru.semperante.learnback.entities.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.semperante.learnback.entities.News;

public interface NewsRepository extends JpaRepository<News, Long>
{
    @Query("SELECT n FROM News n JOIN n.categories c WHERE n.published = true AND c.id = ?1 ")
    Page<News> findByCategory(Long category_id, Pageable pages);

}
