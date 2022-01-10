package ru.semperante.learnback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categories
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<News> news;

    public Categories()
    {
    }

    public Categories(String name)
    {
        this.name = name;
    }


    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }
}
