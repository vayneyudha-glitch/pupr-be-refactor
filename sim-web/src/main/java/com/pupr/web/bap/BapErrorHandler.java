package com.pupr.web.bap;

import com.pupr.domain.bap.BapYearValidationException;
import com.pupr.domain.bap.BapDuplicateException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice(assignableTypes = BapController.class)
public class BapErrorHandler {

    @ExceptionHandler(BapYearValidationException.class)
    public ResponseEntity<LegacyErrorResponse> handleYearValidation(
            BapYearValidationException exception, HttpServletRequest request) {
        return error(HttpStatus.FORBIDDEN, exception.getMessage(), request);
    }

    @ExceptionHandler(BapDuplicateException.class)
    public ResponseEntity<LegacyErrorResponse> handleDuplicate(
            BapDuplicateException exception, HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, exception.getMessage(), request);
    }

    private static ResponseEntity<LegacyErrorResponse> error(
            HttpStatus status, String message, HttpServletRequest request) {
        LegacyErrorResponse body = new LegacyErrorResponse(
                new Date(), status.value(), status.getReasonPhrase(), message, request.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }

    public record LegacyErrorResponse(Date timestamp, int status, String error, String message, String path) {
    }
}
