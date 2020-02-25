package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(LUNCH_ID,BREAKFAST_LUNCH_USER_ID);
        assertMatch(meal,LUNCH);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(10,1);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(BREAKFAST_ID,BREAKFAST_LUNCH_USER_ID);
        service.get(BREAKFAST_ID,BREAKFAST_LUNCH_USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(50,100);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> actual = service.getBetweenHalfOpen(null, LocalDate.of(2020,2,25),BREAKFAST_LUNCH_USER_ID);
        assertMatch(actual,BREAKFAST,LUNCH);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(BREAKFAST_LUNCH_USER_ID);
        assertMatch(meals,BREAKFAST,LUNCH);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated,BREAKFAST_LUNCH_USER_ID);
        assertMatch(service.get(BREAKFAST_ID,BREAKFAST_LUNCH_USER_ID),updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(getUpdated(),DINNER_USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, DINNER_USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created,newMeal);
        assertMatch(service.get(newId, DINNER_USER_ID), newMeal);
    }
}