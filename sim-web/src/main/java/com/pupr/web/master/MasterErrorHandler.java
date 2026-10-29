package com.pupr.web.master;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice(assignableTypes = MasterController.class)
public class MasterErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<LegacyErrorResponse> handleMasterException(
            Exception exception, HttpServletRequest request) {
        LegacyErrorResponse body = new LegacyErrorResponse(
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    public record LegacyErrorResponse(Date timestamp, int status, String error, String message, String path) {
    }
}
