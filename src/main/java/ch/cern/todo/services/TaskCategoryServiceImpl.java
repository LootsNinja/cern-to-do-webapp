package ch.cern.todo.services;

import ch.cern.todo.dtos.TaskCategoryDto;
import ch.cern.todo.exceptions.TaskCategoryIdsDontMatch;
import ch.cern.todo.exceptions.TaskCategoryNotFoundException;
import ch.cern.todo.models.TaskCategoryModel;
import ch.cern.todo.repository.TaskCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskCategoryServiceImpl implements TaskCategoryService {
    private final TaskCategoryRepository taskCategoryRepository;

    public List<TaskCategoryDto> getTaskCategories(String taskCategoryName) {
        if (taskCategoryName != null && !taskCategoryName.isBlank()) {
            return taskCategoryRepository.findTaskCategoryByCategoryName(taskCategoryName).stream().map(TaskCategoryModel::toTaskCategoryDto).toList();
        }
        return taskCategoryRepository.findAll().stream().map(TaskCategoryModel::toTaskCategoryDto).toList();
    }

    public TaskCategoryDto createTaskCategory(TaskCategoryDto taskCategoryDto) {
        if (taskCategoryDto.getCategoryName() != null && !taskCategoryDto.getCategoryName().isBlank()) {
            Optional<TaskCategoryModel> optionalTaskCategory = taskCategoryRepository.findTaskCategoryByCategoryName(taskCategoryDto.getCategoryName()).stream().findAny();
            if (optionalTaskCategory.isPresent()) {
                return optionalTaskCategory.get().toTaskCategoryDto();
            }
        }

        return taskCategoryRepository.save(taskCategoryDto.toTaskCategoryModel()).toTaskCategoryDto();
    }

    @Override
    public TaskCategoryDto getTaskCategoryById(Long id) {
        return taskCategoryRepository.findById(id).map(TaskCategoryModel::toTaskCategoryDto)
                .orElseThrow(() -> new TaskCategoryNotFoundException(id));
    }

    @Override
    public void deleteTaskCategory(Long id) {
        taskCategoryRepository.deleteById(id);
    }

    @Override
    public Long updateTaskCategory(Long id, TaskCategoryDto taskCategory) {
        if (taskCategory.getCategoryId() == null || Objects.equals(id, taskCategory.getCategoryId())) {
            throw new TaskCategoryIdsDontMatch(id, taskCategory.getCategoryId());
        }

        if (taskCategoryRepository.findById(taskCategory.getCategoryId()).isPresent()) {
            TaskCategoryModel savedTaskCategory = taskCategoryRepository.save(taskCategory.toTaskCategoryModel());
            return savedTaskCategory.getCategoryId();
        }

        throw new TaskCategoryNotFoundException(taskCategory.getCategoryId());
    }
}
