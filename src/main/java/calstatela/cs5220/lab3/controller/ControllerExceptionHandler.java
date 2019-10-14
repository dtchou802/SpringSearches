package calstatela.cs5220.lab3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleFormatException(InvalidFormatException ex) {
        logger.error("Serialization/Deserialization failed", ex);
        return new ResponseEntity<String>("Wrong data format", HttpStatus.BAD_REQUEST);
    }
}
