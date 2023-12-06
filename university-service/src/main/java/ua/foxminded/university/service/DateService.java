package ua.foxminded.university.service;

import java.time.LocalDate;

public interface DateService {
    LocalDate getCurrentDate();

    LocalDate getNextDayOfMonth();
}
