package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.university.repository.TeacherAccountRepository;
import ua.foxminded.university.service.TeacherAccountService;

@Service
@AllArgsConstructor
public class TeacherAccountServiceImpl implements TeacherAccountService {
    private final TeacherAccountRepository teacherAccountRepository;
}
