package ua.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.university.service.DateService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateServiceImpl implements DateService {

    private static final int NUMBER_OF_TWO_WEEK = 14;

    @Override
    public LocalDate getCurrentDate() {

        return LocalDate.now();
    }

    @Override
    public LocalDate getNextDayOfMonth() {

        return getCurrentDate().plusDays(1);
    }

    @Override
    public List<LocalDate> getNearNextTwoWeeks() {
        List<LocalDate> listOfNextTwoWeeks = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_TWO_WEEK; i++) {
            LocalDate nextDate = getCurrentDate().plusDays(i);

            listOfNextTwoWeeks.add(nextDate);
        }

        return listOfNextTwoWeeks;
    }
}
