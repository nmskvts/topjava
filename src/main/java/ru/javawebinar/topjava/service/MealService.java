package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal,userId);
    }

    public void delete(int id, int userId) {
        ValidationUtil.checkNotFound(repository.delete(id, userId), "id = " + id + ", userId = " + userId);
    }

    public Meal get(int id, int userId) {
        return ValidationUtil.checkNotFound(repository.get(id,userId),"id = " + id + ", userId = " + userId);
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void update(Meal meal,int userId) {
        ValidationUtil.checkNotFound(repository.save(meal,userId),"id = " + meal.getId() + ", userId = " + userId);
    }

}