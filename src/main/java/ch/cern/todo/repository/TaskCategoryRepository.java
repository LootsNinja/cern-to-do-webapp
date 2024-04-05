package ch.cern.todo.repository;

import ch.cern.todo.models.TaskCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskCategoryRepository extends JpaRepository<TaskCategoryModel, Long> {
    Optional<TaskCategoryModel> findTaskCategoryByCategoryName(String categoryName);
}
