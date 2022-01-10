package ru.semperante.learnback.config;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Configuration
public class CronConfig
{
    /**
     * Для логирования
     */
    private static final Logger LOGGER = Logger.getLogger(CronConfig.class);

    /**
     * Для взятия подключения к БД
     */
    private final DataSource dataSource;


    public CronConfig(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    /**
     * Задание выполняется каждые полчаса - удаляет JWT которые вызывали logout, но уже истекли
     * Т.к. нет смысла далее хранить их в БД (они и без того невалидные теперь)
     */
    @Scheduled(cron = "00,30 * * * * ")
    public void doClearTokens()
    {
        try (Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM logout_tokens WHERE expired_at < CURRENT_TIMESTAMP");
            LOGGER.infof("Deleted %d expired tokens", ps.executeUpdate());
            ps.close();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Задание выполняется каждую минуту - публикует "отложенные" новости
     */
    @Scheduled(cron = "* * * * * *")
    public void doPublishNews()
    {
        try (Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE news SET published = true, publish_at = NULL, created_at = CURRENT_TIMESTAMP WHERE publish_at < CURRENT_TIMESTAMP"
            );
            LOGGER.infof("Published %d news", ps.executeUpdate());
            ps.close();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
