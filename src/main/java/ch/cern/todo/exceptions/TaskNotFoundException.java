package ch.cern.todo.exceptions;

import static ch.cern.todo.constants.Constants.TASK_NOT_FOUND_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class TaskNotFoundException extends ApplicationException {

    public TaskNotFoundException(final Long id) {
        super(NOT_FOUND, TASK_NOT_FOUND_ERROR_MESSAGE.formatted(id));
    }
}
