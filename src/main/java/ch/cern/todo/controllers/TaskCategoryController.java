package ch.cern.todo.controllers;

import ch.cern.todo.api.ApiResponse;
import ch.cern.todo.dtos.TaskCategoryDto;
import ch.cern.todo.services.TaskCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ch.cern.todo.constants.Constants.CATEGORIES_REQUEST_MAPPING;
import static ch.cern.todo.constants.Constants.ID_PATH_PARAMETER;

@RestController
@RequestMapping(CATEGORIES_REQUEST_MAPPING)
@RequiredArgsConstructor
public class TaskCategoryController {
    private final TaskCategoryService taskCategoryService;

    @PostMapping
    public ApiResponse<TaskCategoryDto> createTaskCategory(@RequestBody TaskCategoryDto taskCategoryModel) {
        TaskCategoryDto createdTaskCategoryModel = taskCategoryService.createTaskCategory(taskCategoryModel);
        return ApiResponse.created(createdTaskCategoryModel);
    }

    @GetMapping
    public ApiResponse<List<TaskCategoryDto>> getTaskCategories(@RequestParam(value = "categoryName", required = false) String categoryName) {
        return ApiResponse.ok(taskCategoryService.getTaskCategories(categoryName));
    }

    @GetMapping(ID_PATH_PARAMETER)
    public ApiResponse<TaskCategoryDto> getTaskCategoryById(@PathVariable Long id) {
        return ApiResponse.ok(taskCategoryService.getTaskCategoryById(id));
    }

    @DeleteMapping(ID_PATH_PARAMETER)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTaskCategory(@PathVariable Long id) {
        taskCategoryService.deleteTaskCategory(id);
    }

    @PutMapping(ID_PATH_PARAMETER)
    public ApiResponse<Long> updateTaskCategory(@PathVariable Long id, @RequestBody TaskCategoryDto taskCategory) {
        taskCategory.setCategoryId(id);
        return ApiResponse.ok(taskCategoryService.updateTaskCategory(id, taskCategory));
    }
}
