package hw2_13.com.example.hw2_13.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class EmployeeWrongNameException extends RuntimeException{
    public EmployeeWrongNameException(String message) {
        super(message);
    }
}
