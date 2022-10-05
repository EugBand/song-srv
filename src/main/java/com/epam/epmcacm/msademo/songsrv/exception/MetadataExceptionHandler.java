package com.epam.epmcacm.msademo.songsrv.exception;

import com.epam.epmcacm.msademo.songsrv.dto.ErrorResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MetadataExceptionHandler {

    @ResponseBody
    @ExceptionHandler({ BadRequestException.class, EmptyResultDataAccessException.class,
            MissingServletRequestParameterException.class })
    public ResponseEntity<Object> constraintViolationException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseDto.builder()
                .timestamp(Instant.now().toString())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getContextPath())

                .build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> commonServerException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseDto.builder()
                .timestamp(Instant.now().toString())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
