package ch.cern.todo.services;

import ch.cern.todo.dtos.TaskDto;

import java.util.List;

public interface TasksService {
    Long createTask(TaskDto task);

    List<TaskDto> getTasks(String taskName);

    TaskDto getTaskById(long id);

    void deleteTask(long id);

    Long updateTask(Long id, TaskDto taskDto);
}
