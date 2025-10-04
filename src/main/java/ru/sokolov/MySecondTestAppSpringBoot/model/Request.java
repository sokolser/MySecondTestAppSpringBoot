package ru.sokolov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import  lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Request {

    @NotBlank (message = "uid является обязательным полем")
    @Size (max = 32, message = "UID не может превышать 32 символа")
    private String uid;

    @NotBlank(message = "Operation UID является обязательным полем")
    @Size(max = 32, message = "Operation UID не может превышать 32 символа")
    private String operationUid;
    private String systemName;

    @NotBlank(message = "System Time является обязательным полем")
    private String systemTime;
    private String source;

    @Min(value = 1, message = "Communication ID должен быть не менее 1")
    @Max(value = 100000, message = "Communication ID должен быть не более 100000")
    private int communicationId;
    private int templateId;
    private int productCode;
    private int smsCode;

    @Override
    public String toString() {
        return "{" +
                "uid+'" +uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communacationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" +productCode +
                ", smsCode=" + smsCode +
                '}';
    }
}
