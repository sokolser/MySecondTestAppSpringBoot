package ru.sokolov.MySecondTestAppSpringBoot.service;

import org.springframework.validation.BindingResult;
import ru.sokolov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}