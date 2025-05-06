package org.example.swe304.Exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;


@ControllerAdvice
public class GlobalExceptionHandler {

    private List<String> addValueMap(List<String> list, String value) {
        list.add(value);
        return list;

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<APIErrors> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errorsMap = new HashMap<>();

        //aslında bütün hata mesajlarımız burada toplanıyor
        // bunları foreach ile dönüyoruz ve hata mesajlarını alıyoruz
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objectError).getField();
            if (errorsMap.containsKey(fieldName)) {
                errorsMap.put(fieldName, addValueMap(errorsMap.get(fieldName), objectError.getDefaultMessage()));
            } else {
                errorsMap.put(fieldName, new ArrayList<>());
            }
        }
        return ResponseEntity.badRequest().body(CreateAPIError(errorsMap));

    }

    private <T> APIErrors<T> CreateAPIError(T errors) {
        APIErrors<T> apiErrors = new APIErrors<T>();
        apiErrors.setErrorId(UUID.randomUUID().toString());
        apiErrors.setErrorTime(new Date());
        apiErrors.setErrors(errors);
        return apiErrors;

    }
}