package ru.semperante.learnback.dto.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public record UserEditRequest(
        @Size(min = 3, max = 32) String login,
        @Email String email,
        @Size(min = 8, max = 32) String password

)
{
}
