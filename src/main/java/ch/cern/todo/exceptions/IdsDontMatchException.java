package ch.cern.todo.exceptions;

import static ch.cern.todo.constants.Constants.IDS_DONT_MATCH_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class IdsDontMatchException extends ApplicationException {
    public IdsDontMatchException(final Long pathId, final Long bodyId) {
        super(BAD_REQUEST, IDS_DONT_MATCH_ERROR_MESSAGE.formatted(pathId, bodyId));
    }
}
