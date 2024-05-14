package com.infinities_x.rest.service;

import com.infinities_x.rest.dao.TaskDAO;
import com.infinities_x.rest.model.Task;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.List;

@Stateless
public class TaskService {

    @Inject
    private TaskDAO taskDAO;

    public Task findTaskById(Long id) {
        return taskDAO.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    public Response saveTask(Task task) {
        if (task.getId() == null && taskDAO.existsByTitle(task.getTitle())) {
            return Response.status(Response.Status.CONFLICT).entity("Title already exists.").build();
        }
        if (task.getId() == null) {
            taskDAO.create(task);
        } else {
            taskDAO.update(task);
        }
        return Response.status(Response.Status.CREATED).entity(task).build();
    }


    public void deleteTask(Long id) {
        Task task = taskDAO.findById(id);
        if (task != null) {
            taskDAO.delete(task);
        }
    }
}
