package ru.sokolov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.sokolov.MySecondTestAppSpringBoot.model.enums.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuarterBonusServiceImplTest {

    @Test
    void calculate() {
        Positions position = Positions.PM;
        double bonus = 3.0;
        int workDays = 35;
        double salary = 100000.00;
        int year = 2025;
        int quarter = 2;

        double result = new QuarterBonusServiceImpl().calculate(position, salary, bonus, workDays, year, quarter);

        double expected = 1872000.0;
        assertThat(result).isEqualTo(expected);
    }
}