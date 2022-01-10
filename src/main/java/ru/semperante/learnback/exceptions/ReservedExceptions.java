package ru.semperante.learnback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class ReservedExceptions
{
    public static final ResponseStatusException INVALID_TOKEN = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is invalid");
    public static final ResponseStatusException JWT_EXPIRED = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is expired");
    public static final ResponseStatusException DB_ERROR = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Db error occurred");


    public static final ResponseStatusException USER_EXISTS = new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email or Login already used");
    public static final ResponseStatusException USER_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found");
    public static final ResponseStatusException INVALID_PASSWORD = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is invalid");

    public static final ResponseStatusException CATEGORY_EXISTS = new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Category already exists");
    public static final ResponseStatusException CATEGORY_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");

    public static final ResponseStatusException NEWS_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "News not found");

}
