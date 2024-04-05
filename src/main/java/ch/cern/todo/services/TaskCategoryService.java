package ch.cern.todo.services;

import ch.cern.todo.dtos.TaskCategoryDto;

import java.util.List;

public interface TaskCategoryService {
    TaskCategoryDto createTaskCategory(TaskCategoryDto taskCategoryModel);

    List<TaskCategoryDto> getTaskCategories(String taskCategoryName);

    TaskCategoryDto getTaskCategoryById(Long id);

    void deleteTaskCategory(Long id);

    Long updateTaskCategory(Long id, TaskCategoryDto taskCategory);
}
