-- Таблица хранящая новости
CREATE TABLE IF NOT EXISTS news
(
    id         serial8 PRIMARY KEY,                                             -- id
    author_id  int8 REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE, --автор новости
    title      TEXT NOT NULL,                                                   --заголовок новости
    short_text TEXT NOT NULL,                                                   --краткий текст
    full_text  TEXT NOT NULL,                                                   --полный текст
    published  BOOLEAN   DEFAULT FALSE,                                         -- опубликована?
    pinned     BOOLEAN   DEFAULT FALSE,                                         -- закреплена?
    publish_at TIMESTAMP,                                                       -- дата и время отложенной публикации
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                             -- опубликована в
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP                              -- последнее редактирование в
);
CREATE INDEX ON news (author_id); --для поиска по автору
CREATE INDEX ON news (published); -- для вывода опубликованных
CREATE INDEX ON news (pinned DESC); -- для вывода закрепленных ПЕРВЫМИ (false < true, я проверял)
CREATE INDEX ON news (created_at DESC);
-- для вывода сначала свежих
--Таблица связывающая новости и категории мн <-> мн
CREATE TABLE IF NOT EXISTS news_categories
(
    news_id       int8 REFERENCES news (id) ON DELETE CASCADE ON UPDATE CASCADE, --id новости
    categories_id int8 REFERENCES categories (id) ON DELETE CASCADE ON UPDATE CASCADE, --id категории
    PRIMARY KEY (news_id, categories_id) -- они и определяют запись в этой таблице
);
CREATE INDEX ON news_categories (news_id); -- Для взятия категорий по новости
CREATE INDEX ON news_categories (categories_id); -- Для взятия новостей по категории