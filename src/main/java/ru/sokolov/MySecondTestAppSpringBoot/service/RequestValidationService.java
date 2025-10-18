package ru.sokolov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.sokolov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestValidationService.class);

    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            log.error("[VALIDATION] Ошибка валидации: {}", errorMessage);
            throw new ValidationFailedException(errorMessage);
        }
    }
}
