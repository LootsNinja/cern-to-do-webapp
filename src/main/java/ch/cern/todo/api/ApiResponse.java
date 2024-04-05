package ch.cern.todo.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private int statusCode;

    private String statusMessage;

    @JsonInclude(NON_NULL)
    private T response;

    public static <T> ApiResponse<T> ok(T responseObject) {
        return buildResponse(OK, responseObject);
    }

    public static <T> ApiResponse<T> created(T responseObject) {
        return buildResponse(CREATED, responseObject);
    }

    public static <T> ApiResponse<T> buildResponse(HttpStatus status, T responseObject) {
        return new ApiResponse<>(status.value(), status.getReasonPhrase(), responseObject);
    }
}