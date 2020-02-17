package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer,Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final int USER_ID = 1;
    private static final int ADMIN_ID = 2;
    {
        MealsUtil.MEALS.forEach(meal -> save(meal,USER_ID));
        save(new Meal(LocalDateTime.of(2020,2,16,19,30),"Dinner",350),ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020,2,16,10, 0),"Breakfast",500),ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020,2,16,15,30),"Lunch",500),ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (!repository.containsKey(userId)) {
            meal.setId(counter.incrementAndGet());
            repository.put(userId, new ConcurrentHashMap<>());
            repository.get(userId).put(meal.getId(),meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream().
                sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

