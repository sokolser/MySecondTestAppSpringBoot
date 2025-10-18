package ru.sokolov.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.Codes;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.ErrorCodes;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.ErrorMessages;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.Systems;

@Data
@Builder
public class Response {
     /**
      * Уникальный идентификатор сообщения
      */
     private String uid;

     /**
      * Уникальный идентификатор операции
      */
     private String operationUid;

     /**
      * Имя системы отправителя
      */
     private Systems systemName;

     /**
      * Время создания сообщения
      */
     private String systemTime;

     /**
      * Код ответа
      */
     private Codes code;

     /**
      * Годовая премия
      */
     private Double annualBonus;

     /**
      * Квартальная премия
      */
     private Double quarterlyBonus;

     /**
      * Код ошибки
      */
     private ErrorCodes errorCode;

     /**
      * Сообщение об ошибке
      */
     private ErrorMessages errorMessage;
}