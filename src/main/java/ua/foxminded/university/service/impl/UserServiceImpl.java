package ua.foxminded.university.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.UserRepository;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.entity.Teacher;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.Status;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.UserService;
import ua.foxminded.university.validator.UserValidator;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
	userValidator.validateData(userRegistrationRequest.getEmail(), userRegistrationRequest.getFirstName(), userRegistrationRequest.getLastName());
	userValidator.validatePassword(userRegistrationRequest.getPassword(), userRegistrationRequest.getPasswordCheck());
	

	if (userRegistrationRequest.getStatus().equals(Status.TEACHER)) {
	    Teacher newTeacher = new Teacher(userRegistrationRequest.getFirstName(), userRegistrationRequest.getLastName(), userRegistrationRequest.getEmail(),
		    passwordEncoder.encode(userRegistrationRequest.getPassword()), passwordEncoder.encode(userRegistrationRequest.getPasswordCheck()),
		    Status.NEW, RegistrationStatus.NEW);
	    	userRepository.save(newTeacher);

	} if (userRegistrationRequest.getStatus().equals(Status.STUDENT)) {
	    Student newStudent = new Student(userRegistrationRequest.getFirstName(), userRegistrationRequest.getLastName(), userRegistrationRequest.getEmail(),
		    passwordEncoder.encode(userRegistrationRequest.getPassword()), passwordEncoder.encode(userRegistrationRequest.getPasswordCheck()),
		    Status.NEW, RegistrationStatus.NEW);
		userRepository.save(newStudent);
	}
    }
}
