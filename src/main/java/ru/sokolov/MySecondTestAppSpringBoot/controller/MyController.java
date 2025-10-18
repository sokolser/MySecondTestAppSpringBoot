package ru.sokolov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sokolov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.sokolov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.sokolov.MySecondTestAppSpringBoot.model.*;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.Codes;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.ErrorCodes;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.ErrorMessages;
import ru.sokolov.MySecondTestAppSpringBoot.service.*;
import ru.sokolov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;
    private final AnnualBonusService annualBonusService;
    private final QuarterBonusService quarterBonusService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        @Qualifier("ModifyComplexRequestService") ModifyRequestService modifyRequestService,
                        AnnualBonusService annualBonusService,
                        QuarterBonusService quarterBonusService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.annualBonusService = annualBonusService;
        this.quarterBonusService = quarterBonusService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("начало запроса");
        log.info("[CONTROLLER] request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemName(request.getSystemName())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("[CONTROLLER] response: {}", response);

        try {
            validationService.isValid(bindingResult);
        } catch (UnsupportedCodeException e) {
            return generateErrorResponse(response, ErrorCodes.UNSUPPORTED_EXCEPTION, ErrorMessages.UNSUPPORTED, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidationFailedException e) {
            return generateErrorResponse(response, ErrorCodes.VALIDATION_EXCEPTION, ErrorMessages.VALIDATION, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return generateErrorResponse(response, ErrorCodes.UNKNOWN_EXCEPTION, ErrorMessages.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setAnnualBonus(annualBonusService.calculate(
                request.getPosition(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays(),
                Year.now().getValue()));

        if (request.getPosition().isManager()) {
            response.setQuarterlyBonus(quarterBonusService.calculate(
                    request.getPosition(),
                    request.getSalary(),
                    request.getBonus(),
                    request.getWorkDays(),
                    Year.now().getValue(),
                    getCurrentQuarter()
            ));
        }

//        modifyRequestService.modify(request);

        Response modifiedResponse = modifyResponseService.modify(response);

        log.info("[CONTROLLER]response modify: {}", response);
        log.info("[CONTROLLER]конец запроса");

        return new ResponseEntity<>(modifiedResponse, HttpStatus.OK);
    }

    private ResponseEntity<Response> generateErrorResponse(Response response, ErrorCodes code, ErrorMessages message,
                                                           HttpStatus status) {
        response.setCode(Codes.FAILED);
        response.setErrorCode(code);
        response.setErrorMessage(message);
        return new ResponseEntity<>(response, status);
    }

    private int getCurrentQuarter() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int monthValue = calendar.get(Calendar.MONTH);
        return monthValue / 3;
    }
}