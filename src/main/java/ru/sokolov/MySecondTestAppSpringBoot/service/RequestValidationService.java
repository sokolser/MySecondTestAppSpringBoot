package ru.sokolov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.sokolov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Service
public class RequestValidationService implements ValidationService {

    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new ValidationFailedException(errorMessage);
        }
    }
}