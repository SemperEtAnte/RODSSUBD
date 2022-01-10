package ru.semperante.learnback.dto.responses;

import java.sql.Timestamp;

public record NewsListResponse(Long id, String short_text, Boolean pinned, Timestamp created_at, Timestamp updated_at)
{
}
