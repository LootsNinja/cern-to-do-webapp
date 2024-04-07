package ch.cern.todo.dtos;

import ch.cern.todo.models.TaskModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDto {
    private Long taskId;

    @NotBlank
    private String taskName;

    private String taskDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    private Long taskCategoryId;

    private String taskCategoryName;

    public TaskModel toTaskModel() {
        return TaskModel.builder()
                .taskId(this.getTaskId())
                .taskName(this.getTaskName())
                .taskDescription(this.getTaskDescription())
                .deadline(this.getDeadline() == null ? null : Timestamp.valueOf(this.getDeadline()))
                .taskCategory(TaskCategoryDto.builder()
                        .categoryId(this.getTaskCategoryId())
                        .categoryName(this.getTaskCategoryName())
                        .build().toTaskCategoryModel())
                .build();
    }

}
