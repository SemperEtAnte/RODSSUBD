package ru.semperante.learnback.services;


import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.semperante.learnback.dto.requests.CreateCategoryRequest;
import ru.semperante.learnback.entities.Categories;
import ru.semperante.learnback.entities.repositories.CategoriesRepository;
import ru.semperante.learnback.exceptions.ReservedExceptions;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends AParentService
{
    private final CategoriesRepository categoriesRepository;

    public CategoryService(CategoriesRepository categoriesRepository)
    {
        this.categoriesRepository = categoriesRepository;
    }

    public Categories create(CreateCategoryRequest request)
    {
        if (categoriesRepository.existsByName(request.name()))
        {
            throw ReservedExceptions.CATEGORY_EXISTS;
        }
        return categoriesRepository.save(new Categories(request.name()));
    }

    public Categories edit(CreateCategoryRequest request, Long id)
    {
        Optional<Categories> categoriesOptional = categoriesRepository.findById(id);
        if (categoriesOptional.isEmpty())
        {
            throw ReservedExceptions.CATEGORY_NOT_FOUND;
        }
        Categories categories = categoriesOptional.get();
        if (request.name() != null && !request.name().isBlank())
        {
            Optional<Categories> categoriesOpt1 = categoriesRepository.findByName(request.name());
            if (categoriesOpt1.isPresent())
            {
                Categories cat1 = categoriesOpt1.get();
                if (!cat1.getId().equals(categories.getId()))
                {
                    throw ReservedExceptions.CATEGORY_EXISTS;
                }
            }
            else
            {
                categories.setName(request.name());
            }
        }
        return categoriesRepository.save(categories);
    }

    public List<Categories> doListAll()
    {
        return categoriesRepository.findAll(Sort.by("name").ascending());
    }

    public void delete(Long id)
    {
        Optional<Categories> categoriesOpt = categoriesRepository.findById(id);
        if (categoriesOpt.isEmpty())
        {
            throw ReservedExceptions.CATEGORY_NOT_FOUND;
        }
        categoriesRepository.delete(categoriesOpt.get());
    }

}
