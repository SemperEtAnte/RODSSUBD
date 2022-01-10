package ru.semperante.learnback.utils.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.semperante.learnback.entities.User;
import ru.semperante.learnback.entities.repositories.UserRepository;
import ru.semperante.learnback.exceptions.ReservedExceptions;

import java.util.Optional;

@Service
public class AuthorizedUserService implements UserDetailsService
{
    private final UserRepository userRepository;

    public AuthorizedUserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {
        Optional<User> user = userRepository.findById(Long.valueOf(s));
        if (user.isEmpty())
        {
            throw ReservedExceptions.INVALID_TOKEN;
        }
        return user.get();
    }
}