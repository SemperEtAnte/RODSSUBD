package ru.semperante.learnback.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.semperante.learnback.dto.requests.LoginRequest;
import ru.semperante.learnback.dto.requests.RegisterRequest;
import ru.semperante.learnback.dto.requests.UserEditRequest;
import ru.semperante.learnback.dto.responses.AuthResponse;
import ru.semperante.learnback.entities.User;
import ru.semperante.learnback.entities.repositories.UserRepository;
import ru.semperante.learnback.exceptions.ReservedExceptions;
import ru.semperante.learnback.utils.jwt.JWTPayload;
import ru.semperante.learnback.utils.jwt.JWTUtils;

import java.util.Optional;

@Service
public class UserService extends AParentService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtils jwtUtils)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponse doRegister(RegisterRequest req)
    {
        if (userRepository.existsByCredentials(req.login(), req.email()))
        {
            throw ReservedExceptions.USER_EXISTS;
        }
        User user = userRepository.save(new User(req.login(), req.email(), passwordEncoder.encode(req.password())));
        return new AuthResponse(jwtUtils.writeJWT(new JWTPayload(user.getId())));
    }

    public AuthResponse doLogin(LoginRequest request)
    {
        Optional<User> userOpt = userRepository.findByCredentials(request.login());
        if (userOpt.isEmpty())
        {
            throw ReservedExceptions.USER_NOT_FOUND;
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
        {
            throw ReservedExceptions.INVALID_PASSWORD;
        }
        return new AuthResponse(jwtUtils.writeJWT(new JWTPayload(user.getId())));
    }

    public User doMe()
    {
        return getAuthorizedUser();
    }

    public User doEdit(UserEditRequest request)
    {
        User user = getAuthorizedUser();
        if (request.login() != null && !request.login().isBlank())
        {
            Optional<User> userLogOpt = userRepository.findByLogin(request.login());
            if (userLogOpt.isPresent())
            {
                User userLog = userLogOpt.get();
                if (!userLog.getId().equals(user.getId()))
                {
                    throw ReservedExceptions.USER_EXISTS;
                }
            }
            else
            {
                user.setLogin(request.login());
            }
        }
        if (request.email() != null && !request.email().isBlank())
        {
            Optional<User> userLogOpt = userRepository.findByEmail(request.email());
            if (userLogOpt.isPresent())
            {
                User userLog = userLogOpt.get();
                if (!userLog.getId().equals(user.getId()))
                {
                    throw ReservedExceptions.USER_EXISTS;
                }
            }
            else
            {
                user.setEmail(request.email());
            }
        }
        if (request.password() != null && !request.password().isBlank())
        {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        return userRepository.save(user);
    }
}
