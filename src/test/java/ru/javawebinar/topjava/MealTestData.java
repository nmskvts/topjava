package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int BREAKFAST_ID = 100_002;
    public static final int LUNCH_ID = 100_003;
    public static final int DINNER_ID = 100_004;

    public static final int BREAKFAST_LUNCH_USER_ID = 100_001;
    public static final int DINNER_USER_ID = 100_000;

    public static final Meal BREAKFAST = new Meal(BREAKFAST_ID,
            LocalDateTime.of(2020,2, 24, 10, 30),
            "Breakfast",500);
    public static final Meal LUNCH = new Meal(LUNCH_ID,
            LocalDateTime.of(2020,2, 24, 15, 45),
            "Lunch",565);
    public static final Meal DINNER = new Meal(DINNER_ID,
            LocalDateTime.of(2020,2, 24, 19, 20),
            "Dinner",300);

    public static Meal getNew() {
        return new Meal(LocalDateTime.MIN,"Meal",1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(BREAKFAST);
        updated.setCalories(850);
        updated.setDescription("Breakfast and lunch");
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "userId");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("userId").isEqualTo(expected);
    }
}
