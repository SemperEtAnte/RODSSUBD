package ru.semperante.learnback.services;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.semperante.learnback.entities.User;

public abstract class AParentService
{
    protected User getAuthorizedUser()
    {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
