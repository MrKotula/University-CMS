package ua.foxminded.university.service;

import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.exceptions.ValidationException;

public interface UserService {
    void register(UserDto userDto) throws ValidationException; 
}
