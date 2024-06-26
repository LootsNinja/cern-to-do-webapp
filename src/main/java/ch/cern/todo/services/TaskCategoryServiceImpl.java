package ch.cern.todo.services;

import ch.cern.todo.dtos.TaskCategoryDto;
import ch.cern.todo.exceptions.IdsDontMatchException;
import ch.cern.todo.exceptions.TaskCategoryNotFoundException;
import ch.cern.todo.models.TaskCategoryModel;
import ch.cern.todo.repository.TaskCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskCategoryServiceImpl implements TaskCategoryService {
    private final TaskCategoryRepository taskCategoryRepository;

    public TaskCategoryDto createTaskCategory(TaskCategoryDto taskCategoryDto) {
        if (StringUtils.isNotBlank(taskCategoryDto.getCategoryName())) {
            Optional<TaskCategoryModel> optionalTaskCategory = taskCategoryRepository.findTaskCategoryByCategoryName(taskCategoryDto.getCategoryName()).stream().findAny();
            if (optionalTaskCategory.isPresent()) {
                return optionalTaskCategory.get().toTaskCategoryDto();
            }
        }

        return taskCategoryRepository.save(taskCategoryDto.toTaskCategoryModel()).toTaskCategoryDto();
    }

    public List<TaskCategoryDto> getTaskCategories(String taskCategoryName) {
        if (StringUtils.isNotBlank(taskCategoryName)) {
            return taskCategoryRepository.findTaskCategoryByCategoryName(taskCategoryName).stream().map(TaskCategoryModel::toTaskCategoryDto).toList();
        }
        return taskCategoryRepository.findAll().stream().map(TaskCategoryModel::toTaskCategoryDto).toList();
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
        if (taskCategory.getCategoryId() != null && !Objects.equals(id, taskCategory.getCategoryId())) {
            throw new IdsDontMatchException(id, taskCategory.getCategoryId());
        }

        taskCategory.setCategoryId(id);
        if (taskCategoryRepository.findById(taskCategory.getCategoryId()).isPresent()) {
            TaskCategoryModel savedTaskCategory = taskCategoryRepository.save(taskCategory.toTaskCategoryModel());
            return savedTaskCategory.getCategoryId();
        }

        throw new TaskCategoryNotFoundException(taskCategory.getCategoryId());
    }
}
