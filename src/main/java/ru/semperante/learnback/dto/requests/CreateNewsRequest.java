package ru.semperante.learnback.dto.requests;

import java.sql.Timestamp;
import java.util.List;

public record CreateNewsRequest(
        String title,
        String short_text,
        String full_text,
        Boolean published,
        Boolean pinned,
        Timestamp publish_at,
        List<Long> categories
)
{
}
