package com.restaurant.restaurantapi.exceptions;


import com.restaurant.restaurantapi.dtos.ResponseObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /*@ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseObject> handlingRuntimeException(RuntimeException exception){
        log.error("Exception: ", exception);
        ResponseObject apiResponse = new ResponseObject();

        apiResponse.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(false);

        return ResponseEntity.badRequest().body(apiResponse);
    }*/

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ResponseObject> handlingRuntimeException(RuntimeException exception){
        log.error("Exception: ", exception);
        ResponseObject apiResponse = new ResponseObject();

        apiResponse.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(false);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MultipartException.class)
    public void handleFileUploadingError(HttpServletResponse response, Exception exception) {
        log.warn("Failed to upload attachment", exception);
        try {
            response.sendError(Response.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (IOException e) {
            log.error("Failed to send error response", e);
        }
    }

    /*@ExceptionHandler(value = MultipartException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleFileUploadingError(MultipartException exception) {
        log.warn("Failed to upload attachment", exception);
        return exception.getMessage();
    }*/

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public String handleFileSizeException(MaxUploadSizeExceededException exception){
        log.warn("Failed to upload attachment", exception);
        return exception.getMessage();
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseObject> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ResponseObject apiResponse = new ResponseObject();

        apiResponse.setStatusCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());


        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseObject> handleAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.FORBIDDEN;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseObject.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = AuthenticationException.class)
    ResponseEntity<ResponseObject> handleAuthenticationException(AuthenticationException exception){
        ErrorCode errorCode = ErrorCode.INVALIDEMAILORPASSWORD;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseObject.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    /*@ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseObject> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseObject.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }*/

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ResponseObject> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        ResponseObject apiResponse = new ResponseObject();

        apiResponse.setStatusCode((errorCode.getCode()));
        apiResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseObject handleNoHandlerFoundException(NoHandlerFoundException exception) {
        log.error("No handler found for request", exception);
        return ResponseObject.builder()
                .status(false)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Resource not found")
                .build();
    }
}