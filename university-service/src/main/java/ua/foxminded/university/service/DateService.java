package ua.foxminded.university.service;

import java.time.LocalDate;
import java.util.List;

public interface DateService {
    LocalDate getCurrentDate();

    LocalDate getNextDayOfMonth();

    List<LocalDate> getNearNextTwoWeeks();
}
