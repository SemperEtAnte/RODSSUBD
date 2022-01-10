package ru.semperante.learnback.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.semperante.learnback.dto.requests.CreateNewsRequest;
import ru.semperante.learnback.dto.responses.NewsListResponse;
import ru.semperante.learnback.entities.News;
import ru.semperante.learnback.services.NewsService;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/news")
public class NewsController
{
    private final NewsService newsService;

    public NewsController(NewsService newsService)
    {
        this.newsService = newsService;
    }

    @GetMapping("/list/{category_id}")
    public ResponseEntity<Page<NewsListResponse>> doList(
            @PathVariable Long category_id,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page)
    {
        return ResponseEntity.ok(newsService.getList(category_id, limit, page));
    }
    @PostMapping("/")
    @SecurityRequirement(name = "jwtToken")
    public ResponseEntity<News> doCreate(@Valid @RequestBody CreateNewsRequest req)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.doCreate(req));
    }
    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwtToken")
    public ResponseEntity<News> doEdit(@PathVariable Long id, @RequestBody CreateNewsRequest req)
    {
        return ResponseEntity.ok(newsService.doEdit(req, id));
    }
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "jwtToken")
    public ResponseEntity<Void> doDelete(@PathVariable Long id)
    {
        newsService.doDelete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<News> doInfo(@PathVariable Long id)
    {
        return ResponseEntity.ok(newsService.getInfo(id));
    }

}
