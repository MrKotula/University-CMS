package ua.foxminded.university.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.service.dto.request.CourseRequest;
import ua.foxminded.university.service.dto.response.CourseResponse;
import java.util.HashSet;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "courseId", source = "courseId")
    @Mapping(target = "courseName", source = "courseName")
    @Mapping(target = "courseDescription", source = "courseDescription")
    @Mapping(target = "teachers", source = "teachers")
    @Mapping(target = "numberOfSeats", source = "numberOfSeats")
    @Mapping(target = "seatsAvailable", source = "seatsAvailable")
    Course transformCourseFromDto(CourseResponse courseResponse);

    Course transformCourseFromDto(CourseRequest courseRequest);

    @Mapping(target = "courseId", source = "courseId")
    @Mapping(target = "courseName", source = "courseName")
    @Mapping(target = "courseDescription", source = "courseDescription")
    @Mapping(target = "teachers", source = "teachers")
    @Mapping(target = "numberOfSeats", source = "numberOfSeats")
    @Mapping(target = "seatsAvailable", source = "seatsAvailable")
    CourseResponse transformCourseToDto(Course course);

    @Mapping(target = "courseId", source = "courseId")
    @Mapping(target = "courseName", source = "courseName")
    @Mapping(target = "courseDescription", source = "courseDescription")
    @Mapping(target = "teachers", source = "teachers")
    @Mapping(target = "numberOfSeats", source = "numberOfSeats")
    @Mapping(target = "seatsAvailable", source = "seatsAvailable")
    CourseRequest transformCourseToDtoRequest(Course course);

    List<CourseResponse> transformListCourseToDto(List<Course> course);

    List<CourseRequest> transformListCourseToDtoRequest(List<Course> course);

    HashSet<CourseRequest> transformHashSetCourseToDtoRequest(HashSet<Course> course);

    HashSet<CourseResponse> transformHashSetCourseToDtoResponse(HashSet<Course> course);

    HashSet<Course> transformHashSetCourseFromDtoRequest(HashSet<CourseRequest> listCourseRequest);

    HashSet<Course> transformHashSetCourseFromDtoResponse(HashSet<CourseResponse> listCourseResponse);
}
