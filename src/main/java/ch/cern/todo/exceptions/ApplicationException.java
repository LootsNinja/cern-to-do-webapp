package ch.cern.todo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@ResponseStatus(BAD_REQUEST)
public class ApplicationException extends ErrorResponseException {
    public ApplicationException(HttpStatus responseStatus, String message) {
        this(responseStatus, message, null);
    }

    public ApplicationException(HttpStatus responseStatus, String message, Throwable e) {
        super(responseStatus, e);
        if (StringUtils.hasText(message)) {
            setDetail(message);
        }
    }
}
