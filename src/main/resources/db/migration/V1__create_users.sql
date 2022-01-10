-- Таблица пользователей
CREATE TABLE IF NOT EXISTS users
(
    id         serial8 PRIMARY KEY, -- id
    login      VARCHAR(32) NOT NULL, -- логин
    email      TEXT        NOT NULL, -- почта
    password   TEXT        NOT NULL, -- хэш пароля
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата регистрации
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- дата последнего изменения данных
);
CREATE UNIQUE INDEX ON users (lower(login)); -- CI уникальность
CREATE UNIQUE INDEX ON users (lower(email)); -- CI уникальность почты


-- Т.к. обращения проходят по JWT, после вызова LOGOUT нужно браковать валидный JWT - для того таблица
CREATE TABLE IF NOT EXISTS logout_tokens
(
    token      TEXT PRIMARY KEY, -- сам токен
    expired_at TIMESTAMP NOT NULL -- когда он станет невалидным (все токены имеют срок)
);
