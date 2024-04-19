package com.infinities_x.rest;

import com.infinities_x.rest.model.Task;
import com.infinities_x.rest.service.TaskService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    private TaskService taskService;

    @GET
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GET
    @Path("/{id}")
    public Task getTask(@PathParam("id") Long id) {
        return taskService.findTaskById(id);
    }

    @POST
    public Response createTask(Task task) {
        taskService.saveTask(task);
        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @PUT
    @Path("/{id}")
    public Task updateTask(@PathParam("id") Long id, Task task) {
        task.setId(id);
        taskService.saveTask(task);
        return task;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        taskService.deleteTask(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
