package ru.sokolov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.Positions;

@Service
public interface QuarterBonusService {
    double calculate(Positions position, double salary, double bonus, int workDays, int currentYear, int quarter);
}
