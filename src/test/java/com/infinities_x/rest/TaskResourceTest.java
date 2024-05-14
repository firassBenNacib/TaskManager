package com.infinities_x.rest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.infinities_x.rest.model.Task;
import com.infinities_x.rest.service.TaskService;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class TaskResourceTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskResource taskResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        List<Task> expectedTasks = Arrays.asList(new Task(1L, "Task 1", "Description", null, false));
        when(taskService.getAllTasks()).thenReturn(expectedTasks);
        List<Task> result = taskResource.getAllTasks();
        assertEquals(expectedTasks, result);
    }

    @Test
    void testGetTask() {
        Task expectedTask = new Task(1L, "Task 1", "Description", null, false);
        when(taskService.findTaskById(1L)).thenReturn(expectedTask);
        Task result = taskResource.getTask(1L);
        assertEquals(expectedTask, result);
    }

    @Test
    void testCreateTask() {
        Task newTask = new Task(null, "New Task", "Description", null, false);
        when(taskService.saveTask(any(Task.class))).thenReturn(Response.status(Response.Status.CREATED).entity(newTask).build());
        Response response = taskResource.createTask(newTask);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(taskService).saveTask(newTask);
    }

    @Test
    void testCreateTaskWithExistingTitle() {
        Task newTask = new Task(null, "Existing Title", "Description", null, false);
        when(taskService.saveTask(any(Task.class))).thenReturn(Response.status(Response.Status.CONFLICT).entity("Task with the title already exists.").build());
        Response response = taskResource.createTask(newTask);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        verify(taskService).saveTask(newTask);
    }

    @Test
    void testUpdateTask() {
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", null, false);
        when(taskService.saveTask(any(Task.class))).thenReturn(Response.status(Response.Status.OK).entity(updatedTask).build());
        Task result = taskResource.updateTask(1L, updatedTask);
        assertEquals(updatedTask, result);
        verify(taskService).saveTask(updatedTask);
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskService).deleteTask(1L);
        Response response = taskResource.deleteTask(1L);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(taskService).deleteTask(1L);
    }
}
