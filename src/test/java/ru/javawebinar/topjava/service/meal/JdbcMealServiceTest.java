package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "jdbc")
public class JdbcMealServiceTest extends MealServiceTest {
}
