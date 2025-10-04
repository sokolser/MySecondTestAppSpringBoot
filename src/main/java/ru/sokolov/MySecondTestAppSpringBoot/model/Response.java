package ru.sokolov.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.Codes;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.ErrorCodes;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.ErrorMessages;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Response {
     String uid;
     String operationUid;
     String systemTime;
     Codes code;
     ErrorCodes errorCode;
     ErrorMessages errorMessage;
}