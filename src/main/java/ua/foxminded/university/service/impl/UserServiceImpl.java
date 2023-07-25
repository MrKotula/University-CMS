package ua.foxminded.university.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ua.foxminded.university.dao.repository.UserRepository;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.entity.Teacher;
import ua.foxminded.university.enums.RegistrationStatus;
import ua.foxminded.university.enums.Status;
import ua.foxminded.university.exceptions.ValidationException;
import ua.foxminded.university.service.UserService;
import ua.foxminded.university.validator.UserValidator;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void register(UserDto userDto) throws ValidationException {
	userValidator.validateData(userDto.getEmail(), userDto.getFirstName(), userDto.getLastName());
	userValidator.validatePassword(userDto.getPassword(), userDto.getPasswordCheck());
	

	if (userDto.getStatus().equals(Status.TEACHER)) {
	    Teacher newTeacher = new Teacher(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
		    passwordEncoder.encode(userDto.getPassword()), passwordEncoder.encode(userDto.getPasswordCheck()),
		    Status.NEW, RegistrationStatus.NEW);
	    	userRepository.save(newTeacher);

	} if (userDto.getStatus().equals(Status.STUDENT)) {
	    Student newStudent = new Student(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
		    passwordEncoder.encode(userDto.getPassword()), passwordEncoder.encode(userDto.getPasswordCheck()),
		    Status.NEW, RegistrationStatus.NEW);
		userRepository.save(newStudent);
	}
    }
}
