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
}
