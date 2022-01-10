package ru.semperante.learnback.dto.requests;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String login,
        @NotBlank String password)
{
}
