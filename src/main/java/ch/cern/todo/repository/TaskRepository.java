package ch.cern.todo.repository;

import ch.cern.todo.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    Optional<TaskModel> findTaskByTaskName(String taskName);

    List<TaskModel> findTasksByTaskCategoryCategoryName(String categoryName);
}
