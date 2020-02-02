package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<Integer,Integer> map = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcessList = new LinkedList<>();
        for (UserMeal meal : meals) {
            map.merge(meal.getDateTime().getDayOfMonth(), meal.getCalories(), Integer::sum);
        }
        for (UserMeal meal : meals) {
            int hour = meal.getDateTime().getHour();
            if (hour >= startTime.getHour() && hour <= endTime.getHour()) {
                if (map.get(meal.getDateTime().getDayOfMonth()) <= caloriesPerDay) {
                    userMealWithExcessList.add(new UserMealWithExcess(meal.getDateTime(),
                            meal.getDescription(), meal.getCalories(), false));
                }
                else {
                    userMealWithExcessList.add(new UserMealWithExcess(meal.getDateTime(),
                            meal.getDescription(), meal.getCalories(), true));
                }
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<Integer,Integer> map = meals.stream().collect(Collectors.toMap((m) -> m.getDateTime().getDayOfMonth(),
                UserMeal::getCalories, Integer::sum));
        List<UserMealWithExcess> list = new ArrayList<>();
        meals.stream().filter((m) -> m.getDateTime().getHour() >= startTime.getHour() &&
                m.getDateTime().getHour() <= endTime.getHour()).forEach((m) -> {
            if (map.get(m.getDateTime().getDayOfMonth()) <= caloriesPerDay) {
                list.add(new UserMealWithExcess(m.getDateTime(), m.getDescription(),
                        m.getCalories(), false));
            } else list.add(new UserMealWithExcess(m.getDateTime(), m.getDescription(),
                    m.getCalories(), true));
        });
        return list;
    }
}
