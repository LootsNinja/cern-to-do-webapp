package ch.cern.todo.services;

import ch.cern.todo.dtos.TaskDto;
import ch.cern.todo.exceptions.IdsDontMatchException;
import ch.cern.todo.exceptions.TaskCategoryNotFoundException;
import ch.cern.todo.exceptions.TaskNotFoundException;
import ch.cern.todo.models.TaskCategoryModel;
import ch.cern.todo.models.TaskModel;
import ch.cern.todo.repository.TaskCategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {
    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;

    @Override
    public Long createTask(TaskDto task) {
        TaskModel model = task.toTaskModel();
        getExistingTaskCategory(task).ifPresent(model::setTaskCategory);
        TaskModel savedTask = taskRepository.save(model);
        return savedTask.getTaskId();
    }


    @Override
    public List<TaskDto> getTasks(String taskName) {
        if (StringUtils.isNotBlank(taskName)) {
            return taskRepository.findTaskByTaskName(taskName).stream().map(TaskModel::toTaskDto).toList();
        }
        return taskRepository.findAll().stream().map(TaskModel::toTaskDto).toList();
    }

    @Override
    public TaskDto getTaskById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id)).toTaskDto();
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Long updateTask(Long id, TaskDto taskDto) {
        if (!(taskDto.getTaskId() == null || Objects.equals(id, taskDto.getTaskId()))) {
            throw new IdsDontMatchException(id, taskDto.getTaskId());
        }

        taskDto.setTaskId(id);
        if (taskRepository.findById(taskDto.getTaskId()).isPresent()) {
            var taskModel = taskDto.toTaskModel();
            getExistingTaskCategory(taskDto).ifPresent(taskModel::setTaskCategory);
            var savedTask = taskRepository.save(taskModel);
            return savedTask.getTaskId();
        }

        throw new TaskNotFoundException(taskDto.getTaskId());
    }

    private Optional<TaskCategoryModel> getExistingTaskCategory(TaskDto task) {
        Optional<TaskCategoryModel> taskCategory = Optional.empty();
        if (task.getTaskCategoryId() != null) {
            taskCategory = taskCategoryRepository.findById(task.getTaskCategoryId());
            if (taskCategory.isEmpty()) throw new TaskCategoryNotFoundException(task.getTaskCategoryId());
        } else if (StringUtils.isNotBlank(task.getTaskCategoryName())) {
            taskCategory = taskCategoryRepository.findTaskCategoryByCategoryName(task.getTaskCategoryName());
            if (taskCategory.isEmpty()) throw new TaskCategoryNotFoundException(task.getTaskCategoryName());
        }
        return taskCategory;
    }

}
