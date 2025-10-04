package ru.sokolov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sokolov.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);

}
