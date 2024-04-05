package ch.cern.todo.exceptions;

import static ch.cern.todo.constants.Constants.CATEGORY_NOT_FOUND_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class TaskCategoryNotFoundException extends ApplicationException {

    public TaskCategoryNotFoundException(final Long id) {
        super(NOT_FOUND, CATEGORY_NOT_FOUND_ERROR_MESSAGE.formatted(id));
    }
}
