package pl.coderslab.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.coderslab.web.errors.ApiErrorResponse;
import pl.coderslab.web.errors.BadRequestException;
import pl.coderslab.web.errors.ErrorConstants;
import pl.coderslab.web.errors.FieldError;

import java.util.HashMap;
import java.util.stream.Collectors;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        HashMap<String, Object> params = new HashMap<>();
        params.put("fieldErrors", bindingResult.getFieldErrors()
                                               .stream()
                                               .map(f -> new FieldError(f.getObjectName(), f.getField(), f.getDefaultMessage()))
                                               .collect(Collectors.toList()));

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse("Method argument not valid", HttpStatus.BAD_REQUEST, ErrorConstants.ERR_VALIDATION, params);

        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<Object> badRequestException(BadRequestException ex) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, ex.getErrorKey(), ex
                .getParams());
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }


}