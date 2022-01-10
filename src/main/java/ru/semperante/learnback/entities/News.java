package ru.semperante.learnback.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news")
public class News
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "short_text")
    private String shortText;
    @Column(name = "full_text")
    private String fullText;
    private Boolean published;
    private Boolean pinned;

    @Column(name = "publish_at")
    private Timestamp publishAt;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "author_id")
    private User author;


    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "news_categories",
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            joinColumns = @JoinColumn(name = "news_id")
    )
    private List<Categories> categories;

    public News()
    {
    }

    public News(String title, String shortText, String fullText, Boolean published, Boolean pinned, Timestamp publishAt, User author, List<Categories> categories)
    {
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.published = (published != null && published) && publishAt == null;
        this.pinned = pinned != null && pinned;
        this.publishAt = publishAt;
        this.author = author;
        this.categories = categories;
    }

    public Long getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getShortText()
    {
        return shortText;
    }

    public String getFullText()
    {
        return fullText;
    }

    public Boolean getPublished()
    {
        return published;
    }

    public Boolean getPinned()
    {
        return pinned;
    }

    public Timestamp getPublishAt()
    {
        return publishAt;
    }

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    public Timestamp getUpdatedAt()
    {
        return updatedAt;
    }

    public User getAuthor()
    {
        return author;
    }

    public List<Categories> getCategories()
    {
        if(categories == null)
            categories = new ArrayList<>();
        return categories;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setShortText(String shortText)
    {
        this.shortText = shortText;
    }

    public void setFullText(String fullText)
    {
        this.fullText = fullText;
    }

    public void setPublished(Boolean published)
    {
        this.published = published;
    }

    public void setPinned(Boolean pinned)
    {
        this.pinned = pinned;
    }

    public void setPublishAt(Timestamp publishAt)
    {
        this.publishAt = publishAt;
    }

    public void setCategories(List<Categories> categories)
    {
        this.categories = categories;
    }
}
