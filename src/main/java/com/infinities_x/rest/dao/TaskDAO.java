package com.infinities_x.rest.dao;

import com.infinities_x.rest.model.Task;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class TaskDAO {

    @Inject
    private EntityManager em;

    public Task findById(Long id) {
        return em.find(Task.class, id);
    }

    public List<Task> findAll() {
        return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    public void create(Task task) {
        em.persist(task);
    }

    public Task update(Task task) {
        return em.merge(task);
    }

    public void delete(Task task) {
        em.remove(task);
    }
    public boolean existsByTitle(String title) {
        Long count = em.createQuery("SELECT COUNT(t) FROM Task t WHERE t.title = :title", Long.class)
                       .setParameter("title", title)
                       .getSingleResult();
        return count > 0;
    }

}
