package ru.sokolov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sokolov.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
