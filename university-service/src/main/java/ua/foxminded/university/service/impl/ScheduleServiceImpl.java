package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.repository.ScheduleRepository;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.ScheduleService;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DateService dateService;
    private final StudentAccountService studentAccountService;
    private final GroupService groupService;

    @Override
    public List<Schedule> getListOfScheduleToday(String studentId) {
        GroupResponse groupResponse = groupService.getGroupByUserId(studentId);

        LocalDate currentDate = dateService.getCurrentDate();

        if(groupResponse == null || groupResponse.getGroupName() == null) {
            throw new EntityNotFoundException("GroupResponse return a null!");
        }

        return scheduleRepository.findSchedulesByDateAndGroup(currentDate, groupResponse.getGroupName());
    }

    @Override
    public List<Schedule> getListOfScheduleTomorrow(String studentId) {
        GroupResponse groupResponse = groupService.getGroupByUserId(studentId);

        if(groupResponse == null || groupResponse.getGroupName() == null) {
            throw new EntityNotFoundException("GroupResponse return a null!");
        }

        return scheduleRepository.findSchedulesByDateAndGroup(dateService.getNextDayOfMonth(), groupResponse.getGroupName());
    }
}
