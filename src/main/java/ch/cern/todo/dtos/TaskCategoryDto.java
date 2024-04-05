package ch.cern.todo.dtos;

import ch.cern.todo.models.TaskCategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCategoryDto {
    private Long categoryId;

    @NotBlank
    @NotNull
    private String categoryName;

    private String categoryDescription;

    public TaskCategoryModel toTaskCategoryModel() {
        return TaskCategoryModel.builder()
                .categoryId(this.getCategoryId())
                .categoryName(this.getCategoryName())
                .categoryDescription(this.getCategoryDescription())
                .build();
    }
}
