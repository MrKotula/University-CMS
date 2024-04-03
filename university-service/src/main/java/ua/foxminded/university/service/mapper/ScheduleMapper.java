package ua.foxminded.university.service.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.service.dto.request.ScheduleRequest;
import ua.foxminded.university.service.dto.response.ScheduleResponse;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper extends Converter<Schedule, ScheduleResponse> {

    @IterableMapping(qualifiedByName = "toScheduleResponse")
    List<ScheduleResponse> transformListSchedulesToDto(List<Schedule> schedules);

    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "group", source = "group")
    @Mapping(target = "teacher", source = "teacher")
    @Mapping(target = "lecture", source = "lecture")
    @Mapping(target = "version", source = "version")
    @Named("toScheduleResponse")
    ScheduleResponse transformScheduleToDto(Schedule schedule);

    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "group", source = "group")
    @Mapping(target = "teacher", source = "teacher")
    @Mapping(target = "lecture", source = "lecture")
    @Mapping(target = "version", source = "version")
    @Named("toScheduleRequest")
    ScheduleRequest transformScheduleToDtoRequest(Schedule schedule);

    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "group", source = "group")
    @Mapping(target = "teacher", source = "teacher")
    @Mapping(target = "lecture", source = "lecture")
    @Mapping(target = "version", source = "version")
    @Named("toEntity")
    Schedule transformScheduleFromDto(ScheduleResponse scheduleResponse);

    @IterableMapping(qualifiedByName = "toEntity")
    Schedule transformScheduleFromDto(ScheduleRequest scheduleResponse);

    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "group", source = "group")
    @Mapping(target = "teacher", source = "teacher")
    @Mapping(target = "lecture", source = "lecture")
    @Mapping(target = "version", source = "version")
    @Named("toScheduleRequest")
    ScheduleRequest transformScheduleResponseFromDto(ScheduleResponse scheduleResponse);
}
