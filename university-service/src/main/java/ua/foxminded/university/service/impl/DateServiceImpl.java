package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.university.service.DateService;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DateServiceImpl implements DateService {

    @Override
    public LocalDate getCurrentDate() {

        return LocalDate.now();
    }

    @Override
    public LocalDate getNextDayOfMonth() {

        return getCurrentDate().plusDays(1);
    }
}
