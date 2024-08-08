package com.personal.project.angi.exception.handler;

import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.BadRequestException;
import com.personal.project.angi.exception.UserDoesHavePermission;
import com.personal.project.angi.exception.response.ExceptionMessageResponse;
import com.personal.project.angi.model.dto.ResponseDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public <T> ResponseEntity<ResponseDto<T>> errorResponse(ChangeSetPersister.NotFoundException ex, Locale locale) {
        log.error("Exception occurred: ", ex);
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(ResponseCodeEnum.EXCEPTION0404.toString())
                .build();
        return ResponseEntity.ok(dto);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public <T> ResponseEntity<ResponseDto<T>> missingServletRequestParameterException(MissingServletRequestParameterException ex, Locale locale) {
        log.error("MissingServletRequestParameterException : ", ex);
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(ResponseCodeEnum.EXCEPTION0504.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(BadRequestException.class)
    public <T> ResponseEntity<ResponseDto<T>> badRequest(BadRequestException ex, Locale locale) {
        log.debug("Bad request : ", ex);
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(ResponseCodeEnum.EXCEPTION0400.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ResponseDto<T>> exception(Exception ex, Locale locale) {
        log.error("Exception occurred : ", ex);
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(messageSource.getMessage("exception.exception", null, locale))
                .statusCode(ResponseCodeEnum.EXCEPTION.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static ResponseEntity<ResponseDto<List<ExceptionMessageResponse>>> handleValidationExceptions(MethodArgumentNotValidException ex, Locale locale) {

        List<ExceptionMessageResponse> data = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String[] fieldError = Objects.requireNonNull(error.getCodes())[0].split("\\.");
            data.add(new ExceptionMessageResponse(fieldError[fieldError.length - 1], error.getDefaultMessage()));
        });

        String message = null;

        if (!data.isEmpty()) {
            message = data.get(0).getMessage();
        }

        final ResponseDto<List<ExceptionMessageResponse>> dto = ResponseDto.<List<ExceptionMessageResponse>>
                        builder()
                .success(false)
                .data(data)
                .message(message)
                .statusCode(ResponseCodeEnum.EXCEPTION0400.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public static ResponseEntity<ResponseDto<List<ExceptionMessageResponse>>> handleValidationExceptions(ConstraintViolationException ex, Locale locale) {

        List<ExceptionMessageResponse> data = ex.getConstraintViolations().stream().map(cv -> {
            Path.Node lastNode = null;
            Iterator<Path.Node> interator = cv.getPropertyPath().iterator();
            while (interator.hasNext()) {
                lastNode = interator.next();
            }
            assert lastNode != null;
            return ExceptionMessageResponse.builder().name(lastNode.getName()).message(lastNode.getName()).build();
        }).collect(Collectors.toList());

        String message = null;

        if (!data.isEmpty()) {
            message = data.get(0).getMessage();
        }

        final ResponseDto<List<ExceptionMessageResponse>> dto = ResponseDto.<List<ExceptionMessageResponse>>
                        builder()
                .success(false)
                .data(data)
                .message(message)
                .statusCode(ResponseCodeEnum.EXCEPTION0400.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public <T> ResponseEntity<ResponseDto<T>> deniedExecuted(AccessDeniedException ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(ResponseCodeEnum.EXCEPTION0505.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(UserDoesHavePermission.class)
    public <T> ResponseEntity<ResponseDto<T>> userDoesNotHavePermission(AccessDeniedException ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(ResponseCodeEnum.EXCEPTION0505.toString())
                .build();
        return ResponseEntity.ok(dto);
    }
}
