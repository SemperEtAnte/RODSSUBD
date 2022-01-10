package ru.semperante.learnback.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.semperante.learnback.dto.requests.CreateNewsRequest;
import ru.semperante.learnback.dto.responses.NewsListResponse;
import ru.semperante.learnback.entities.Categories;
import ru.semperante.learnback.entities.News;
import ru.semperante.learnback.entities.repositories.CategoriesRepository;
import ru.semperante.learnback.entities.repositories.NewsRepository;
import ru.semperante.learnback.exceptions.ReservedExceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService extends AParentService
{
    private final NewsRepository newsRepository;
    private final CategoriesRepository categoriesRepository;

    public NewsService(NewsRepository newsRepository, CategoriesRepository categoriesRepository)
    {
        this.newsRepository = newsRepository;
        this.categoriesRepository = categoriesRepository;
    }

    public News doCreate(CreateNewsRequest req)
    {
        List<Categories> categories = null;
        if (req.categories() != null && !req.categories().isEmpty())
        {
            categories = categoriesRepository.findAllById(req.categories());
        }
        return newsRepository.save(new News(req.title(), req.short_text(), req.full_text(), req.published(), req.pinned(), req.publish_at(), getAuthorizedUser(), categories));
    }

    public News doEdit(CreateNewsRequest req, Long id)
    {
        Optional<News> newsOpt = newsRepository.findById(id);
        if (newsOpt.isEmpty())
        {
            throw ReservedExceptions.NEWS_NOT_FOUND;
        }
        News news = newsOpt.get();
        List<Categories> categories = news.getCategories();
        if (req.categories() != null)
        {
            categories.clear();
            if (!req.categories().isEmpty())
            {
                categories.addAll(categoriesRepository.findAllById(req.categories()));
            }
        }
        if (req.title() != null && !req.title().isBlank())
        {
            news.setTitle(req.title());
        }
        if (req.short_text() != null && !req.short_text().isBlank())
        {
            news.setShortText(req.short_text());
        }
        if (req.full_text() != null && !req.full_text().isBlank())
        {
            news.setFullText(req.full_text());
        }
        if (req.pinned() != null)
        {
            news.setPinned(req.pinned());
        }

        if (req.publish_at() != null)
        {
            news.setPublishAt(req.publish_at());
        }
        if (req.published() != null)
        {
            news.setPublished(req.published());
        }
        return newsRepository.save(news);

    }

    public Page<NewsListResponse> getList(Long category_id, Integer limit, Integer page)
    {
        Page<News> news = newsRepository.findByCategory(category_id, PageRequest.of(page == null ? 0 : page, limit == null ? 20 : limit));
        List<NewsListResponse> res = new ArrayList<>(news.getSize());
        for (News ne : news)
        {
            res.add(new NewsListResponse(ne.getId(), ne.getShortText(), ne.getPinned(), ne.getCreatedAt(), ne.getUpdatedAt()));
        }
        return new PageImpl<>(res, news.getPageable(), news.getTotalElements());
    }

    public News getInfo(Long id)
    {
        Optional<News> newsOpt = newsRepository.findById(id);
        if (newsOpt.isEmpty())
        {
            throw ReservedExceptions.NEWS_NOT_FOUND;
        }
        return newsOpt.get();
    }
    public void doDelete(Long id)
    {
        newsRepository.delete(getInfo(id));
    }
}
