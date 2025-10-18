package ru.sokolov.MySecondTestAppSpringBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sokolov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.sokolov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.sokolov.MySecondTestAppSpringBoot.model.Request;
import ru.sokolov.MySecondTestAppSpringBoot.model.Response;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.Codes;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.ErrorCodes;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.ErrorMessages;
import ru.sokolov.MySecondTestAppSpringBoot.service.ValidationService;
import jakarta.validation.Valid;
import ru.sokolov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("начало запроса");
        log.info("[CONTROLLER] request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("[CONTROLLER] response: {}", response);

        try {
            // Проверка на uid = 123
            if ("123".equals(request.getUid())) {
                log.error("[CONTROLLER] UID 123 не поддерживается");
                throw new UnsupportedCodeException("UID 123 не поддерживается");
            }

            log.info("[CONTROLLER] Начало валидации");
            validationService.isValid(bindingResult);
            log.info("[CONTROLLER] Валидация завершена");

        } catch (UnsupportedCodeException e) {
            log.error("[CONTROLLER] Ошибка UnsupportedCodeException: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("[CONTROLLER] response после обработки ошибки UnsupportedCodeException: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (ValidationFailedException e) {
            log.error("[CONTROLLER] Ошибка ValidationFailedException: {}", e.getMessage(), e);
            String allErrors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            log.error("[CONTROLLER] Ошибки валидации: {}", allErrors);

            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("[CONTROLLER] response после обработки ошибки ValidationFailedException: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("[CONTROLLER] Неизвестная ошибка: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("[CONTROLLER] response после обработки неизвестной ошибки: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("конец запроса");
        log.info("[CONTROLLER] response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
