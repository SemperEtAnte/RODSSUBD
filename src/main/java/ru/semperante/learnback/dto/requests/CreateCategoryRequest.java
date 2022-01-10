package ru.semperante.learnback.dto.requests;

import javax.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank String name
)
{
}
