package ru.semperante.learnback.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.semperante.learnback.dto.requests.CreateCategoryRequest;
import ru.semperante.learnback.entities.Categories;
import ru.semperante.learnback.services.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/categories")
@Validated
public class CategoriesController
{
    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    @SecurityRequirement(name = "jwtToken")
    public ResponseEntity<Categories> doCreate(@Valid @RequestBody CreateCategoryRequest req)
    {
        return ResponseEntity.ok(categoryService.create(req));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwtToken")
    public ResponseEntity<Categories> doEdit(@PathVariable Long id, @RequestBody CreateCategoryRequest req)
    {
        return ResponseEntity.ok(categoryService.edit(req, id));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "jwtToken")
    public ResponseEntity<Void> doDelete(@PathVariable Long id)
    {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Categories>> doList()
    {
        return ResponseEntity.ok(categoryService.doListAll());
    }
}
