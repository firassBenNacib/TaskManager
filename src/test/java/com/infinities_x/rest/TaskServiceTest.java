package com.infinities_x.rest;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.infinities_x.rest.dao.TaskDAO;
import com.infinities_x.rest.model.Task;
import com.infinities_x.rest.service.TaskService;

import java.util.Arrays;
import java.util.List;

public class TaskServiceTest {

    @Mock
    private TaskDAO taskDAO;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindTaskById() {
        Task expectedTask = new Task(1L, "Test Task", "Description", null, false);
        when(taskDAO.findById(1L)).thenReturn(expectedTask);
        Task result = taskService.findTaskById(1L);
        assertEquals(expectedTask, result);
    }

    @Test
    void testGetAllTasks() {
        List<Task> expectedTasks = Arrays.asList(new Task(1L, "Task 1", "Description", null, false));
        when(taskDAO.findAll()).thenReturn(expectedTasks);
        List<Task> result = taskService.getAllTasks();
        assertEquals(expectedTasks, result);
    }

    @Test
    void testSaveTaskCreate() {
        Task newTask = new Task(null, "New Task", "Description", null, false);
        doNothing().when(taskDAO).create(newTask);
        assertDoesNotThrow(() -> taskService.saveTask(newTask));
    }

    @Test
    void testSaveTaskUpdate() {
        Task existingTask = new Task(1L, "Existing Task", "Description", null, true);
  
        assertDoesNotThrow(() -> taskService.saveTask(existingTask));
        verify(taskDAO).update(existingTask);  
    }

    @Test
    void testDeleteTask() {
        Task existingTask = new Task(1L, "Existing Task", "Description", null, true);
        when(taskDAO.findById(1L)).thenReturn(existingTask);
        doNothing().when(taskDAO).delete(existingTask);
        assertDoesNotThrow(() -> taskService.deleteTask(1L));
    }
}
