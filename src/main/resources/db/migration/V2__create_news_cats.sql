-- Категории новостей
CREATE TABLE IF NOT EXISTS categories
(
    id         serial8 PRIMARY KEY,                -- id
    name       TEXT NOT NULL,                      -- название
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- когда была создана
);
CREATE UNIQUE INDEX ON categories (lower(name)); -- CI уникальность названия категории