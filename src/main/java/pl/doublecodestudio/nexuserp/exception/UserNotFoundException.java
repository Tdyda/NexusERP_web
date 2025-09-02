package pl.doublecodestudio.nexuserp.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {
    private static final String DEFAULT_MESSAGE = "Użytkownik nie istnieje!";

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, DEFAULT_MESSAGE);
    }

    public UserNotFoundException(HttpStatus status, String message) {
        super(status, (message == null || message.isBlank()) ? DEFAULT_MESSAGE : message);
    }
}
