package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        }
        return em.createNamedQuery("update")
                .setParameter("description", meal.getDescription())
                .setParameter("calories", meal.getCalories())
                .setParameter("date_time", meal.getDateTime())
                .setParameter("id", meal.getId())
                .setParameter("user_id", userId)
                .executeUpdate() != 0 ? meal : null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        try {
            return em.createNamedQuery("delete")
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .executeUpdate() != 0;
        } catch (NoResultException e) {
            return false;
        }

    }

    @Override
    public Meal get(int id, int userId) {
        try {
            return em.createNamedQuery("get", Meal.class)
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery("getAll", Meal.class)
                .setParameter("user_id",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery("getBetweenHalfOpen", Meal.class)
                .setParameter(1,startDate)
                .setParameter(2,endDate)
                .setParameter("user_id", userId)
                .getResultList();
    }
}