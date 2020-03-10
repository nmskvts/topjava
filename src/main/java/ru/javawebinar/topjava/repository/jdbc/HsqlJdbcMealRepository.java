package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@Profile("hsqldb")
public class HsqlJdbcMealRepository extends JdbcMealRepository {

    public HsqlJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public MapSqlParameterSource createMapSql(Meal meal, int userId) {
        return new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", Date.from(meal.getDateTime().atZone(ZoneId.systemDefault()).toInstant()))
                .addValue("user_id", userId);
    }
}
