package com.progresssoft.clustereddatawarehouse.exceptions;


import com.progresssoft.clustereddatawarehouse.dto.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Arrays;

@Log4j2
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException exception) {
        log.error("ERROR:: exception occurred with Message :: {} ::", exception.getMessage());
        HttpStatus status = exception.getStatus() == null ?  HttpStatus.BAD_REQUEST : exception.getStatus();
        ApiResponse<Object> response = ApiResponse.builder()
                .responseStatus(false)
                .responseCode("09")
                .responseMessage(exception.getMessage())
                .build();
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(value = {ResourceCreationException.class})
    public ResponseEntity<ApiResponse<?>> handleResourceCreationException(ResourceCreationException exception) {
        log.error("ERROR:: exception occurred with Message :: {} ::", exception.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse<Object> response = ApiResponse.builder()
                .responseStatus(false)
                .responseCode("09")
                .responseMessage(exception.getMessage())
                .build();
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        log.error("ERROR:: exception occurred with Message :: {} ::", exc.getMessage());
        ApiResponse<Object> response = ApiResponse.builder()
                .responseStatus(false)
                .responseCode("09")
                .responseMessage(exc.getMessage())
                .data("Max file size exceeded")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Object>> handleGlobalExceptions(MethodArgumentNotValidException ex) {
        String[] errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);

        ApiResponse<Object> response = ApiResponse.builder()
                .responseStatus(false)
                .responseCode("09")
                .responseMessage(Arrays.toString(errors))
                .data("Max file size exceeded")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
