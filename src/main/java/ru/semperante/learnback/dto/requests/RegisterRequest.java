package ru.semperante.learnback.dto.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank @Size(min = 3, max = 32) String login,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 32) String password)
{
}
