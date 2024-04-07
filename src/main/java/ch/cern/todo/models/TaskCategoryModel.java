package ch.cern.todo.models;

import ch.cern.todo.dtos.TaskCategoryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import static ch.cern.todo.constants.Constants.*;
import static java.sql.Types.NUMERIC;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = TASK_CATEGORY_TABLE_NAME)
@SequenceGenerator(name = TASK_CATEGORY_SEQ, sequenceName = TASK_CATEGORY_SEQ, allocationSize = 1)
public class TaskCategoryModel {
    @Id
    @Column(name = CATEGORY_ID, updatable = false, columnDefinition = COLUMN_TYPE_NUMBER)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TASK_CATEGORY_SEQ)
    @JdbcTypeCode(value = NUMERIC)
    private Long categoryId;

    @Column(name = CATEGORY_NAME, nullable = false, updatable = false, length = 100, unique = true)
    private String categoryName;

    @Column(name = CATEGORY_DESCRIPTION, length = 500)
    private String categoryDescription;

    public TaskCategoryDto toTaskCategoryDto() {
        return TaskCategoryDto.builder()
                .categoryId(this.getCategoryId())
                .categoryName(this.getCategoryName())
                .categoryDescription(this.getCategoryDescription())
                .build();
    }
}
