package ch.cern.todo.models;


import ch.cern.todo.dtos.TaskDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Timestamp;

import static ch.cern.todo.constants.Constants.*;
import static java.sql.Types.NUMERIC;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TASKS_TABLE_NAME)
@SequenceGenerator(name = TASK_SEQ, sequenceName = TASK_SEQ, allocationSize = 1)
public class TaskModel {

    @Id
    @Column(name = TASK_ID, updatable = false, columnDefinition = COLUMN_TYPE_NUMBER)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TASK_SEQ)
    @JdbcTypeCode(value = NUMERIC)
    private Long taskId;

    @Column(name = TASK_NAME, nullable = false, length = 100)
    private String taskName;

    @Column(name = TASK_DESCRIPTION, length = 500)
    private String taskDescription;

    @Column(name = DEADLINE)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp deadline;

    @ManyToOne
    @JoinColumn(name = CATEGORY_ID, referencedColumnName = CATEGORY_ID)
    private TaskCategoryModel taskCategory;

    public TaskDto toTaskDto() {
        return TaskDto.builder()
                .taskId(this.getTaskId())
                .taskName(this.getTaskName())
                .taskDescription(this.getTaskDescription())
                .deadline(this.getDeadline() == null ? null : this.getDeadline().toLocalDateTime())
                .taskCategoryId(this.getTaskCategory() == null ? null : this.getTaskCategory().getCategoryId())
                .taskCategoryName(this.getTaskCategory() == null ? null : this.getTaskCategory().getCategoryName())
                .build();
    }

}
