package ua.foxminded.university.service.impl;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ua.foxminded.university.dao.repository.StudentRepository;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.enums.RegistrationStatus;
import ua.foxminded.university.enums.Status;
import ua.foxminded.university.exceptions.ValidationException;
import ua.foxminded.university.service.StudentService;
import ua.foxminded.university.validator.UserValidator;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
   
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    
    @Override
    public List<Student> findByCourseName(String courseName) {
	return studentRepository.findByCourseName(courseName);
    }
    
    @Override
    public void addStudentCourse(String studentId, String courseId) {
	studentRepository.addStudentCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(String studentId, String courseId) {
	studentRepository.removeStudentFromCourse(studentId, courseId);
    } 
    
    @Override
    public void deleteById(String id) {
	studentRepository.deleteById(id);
    }
    
    @Override
    public void register(UserDto userDto) throws ValidationException {
	userValidator.validateData(userDto.getEmail(), userDto.getFirstName(), userDto.getLastName());
	userValidator.validatePassword(userDto.getPassword(), userDto.getPasswordCheck());
	
	Student newStudent = new Student(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
		passwordEncoder.encode(userDto.getPassword()), passwordEncoder.encode(userDto.getPasswordCheck()),
		Status.NEW, RegistrationStatus.NEW);

	studentRepository.save(newStudent);
    }

    @Override
    public void updateEmail(Student student) throws ValidationException {
	userValidator.validateEmail(student.getEmail());
	
	studentRepository.save(student);
    }

    @Override
    public void updatePassword(Student student) {
	studentRepository.save(student);
    }

    @Override
    public void updateStatus(Status status, String studentId) {
	studentRepository.updateStatus(status, studentId);
    }

    @Override
    public void changeGroup(String groupId, String studentId) {
	studentRepository.changeGroup(groupId, studentId);
    }
}
