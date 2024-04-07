package ch.cern.todo.controllers;

import ch.cern.todo.api.ApiResponse;
import ch.cern.todo.dtos.TaskDto;
import ch.cern.todo.services.TasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ch.cern.todo.constants.Constants.ID_PATH_PARAMETER;
import static ch.cern.todo.constants.Constants.TASKS_REQUEST_MAPPING;

@RestController
@RequestMapping(TASKS_REQUEST_MAPPING)
@RequiredArgsConstructor
public class TaskController {
    private final TasksService tasksService;

    @PostMapping
    public ApiResponse<Long> createTask(@RequestBody TaskDto task) {
        return ApiResponse.created(tasksService.createTask(task));
    }

    @GetMapping
    public ApiResponse<List<TaskDto>> getTasks(@RequestParam(value = "taskName", required = false) String categoryName) {
        return ApiResponse.ok(tasksService.getTasks(categoryName));
    }

    @GetMapping(ID_PATH_PARAMETER)
    public ApiResponse<TaskDto> getTask(@PathVariable long id) {
        return ApiResponse.ok(tasksService.getTaskById(id));
    }

    @DeleteMapping(ID_PATH_PARAMETER)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable long id) {
        tasksService.deleteTask(id);
    }

    @PutMapping(ID_PATH_PARAMETER)
    public ApiResponse<Long> updateTask(@PathVariable long id, @RequestBody TaskDto taskDto) {
        return ApiResponse.ok(tasksService.updateTask(id, taskDto));
    }
}
