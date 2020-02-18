package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        if (meal.getId() != null) {
            throw new IllegalArgumentException(meal + " id != null");
        }
        log.info("create meal {} for user {}",meal,userId);
        return service.create(meal,userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}",id,userId);
        service.delete(id,userId);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}",id,userId);
        return service.get(id,userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get all meals for user {}",userId);
        return service.getAll(userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        if (meal.isNew()) {
            meal.setId(id);
        }
        else if (meal.getId() != id) {
            throw new IllegalArgumentException(meal + "id != " + id);
        }
        log.info("update meal {} for user {}",id,userId);
        service.update(meal,userId);
    }

}