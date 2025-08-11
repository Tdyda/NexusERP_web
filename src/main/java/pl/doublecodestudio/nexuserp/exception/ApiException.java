package pl.doublecodestudio.nexuserp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final Map<String, String> errors;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.errors = Map.of("error", message);
    }
}
