package ru.sokolov.MySecondTestAppSpringBoot.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCodes {

    EMPTY(""),
    VALIDATION_EXCEPTION("Ошибка валидации"),
    UNSUPPORTED_EXCEPTION("Не поддерживаемая ошибка"),
    UNKNOWN_EXCEPTION("Произошла непредвиденная ошибка");

    private final String name;

    ErrorCodes(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
