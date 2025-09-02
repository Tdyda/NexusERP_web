package pl.doublecodestudio.nexuserp.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException {
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.UNAUTHORIZED;
    private static final String DEFAULT_MESSAGE = "Niepoprawne dane logowania!";

    public InvalidCredentialsException(){
        super(DEFAULT_STATUS, DEFAULT_MESSAGE);
    }

    public InvalidCredentialsException(HttpStatus status, String message) {
        super(status, message);
    }
}
