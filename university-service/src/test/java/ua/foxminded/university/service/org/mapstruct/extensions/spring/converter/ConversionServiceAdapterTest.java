package ua.foxminded.university.service.org.mapstruct.extensions.spring.converter;

import org.junit.jupiter.api.Test;
import org.mapstruct.extensions.spring.converter.ConversionServiceAdapter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.test.util.ReflectionTestUtils;
import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.service.dto.response.ScheduleResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConversionServiceAdapterTest {
    @Test
    void conversionServiceAdapterInitialization() {
        ConversionService dummyConversionService = mock(ConversionService.class);
        ConversionServiceAdapter conversionServiceAdapter = new ConversionServiceAdapter(dummyConversionService);

        ConversionService actualConversionService = (ConversionService) ReflectionTestUtils.getField(conversionServiceAdapter, "conversionService");

        assertNotNull(actualConversionService);
    }

    @Test
    void mapScheduleToScheduleResponse() {
        ConversionService conversionService = mock(ConversionService.class);
        ConversionServiceAdapter conversionServiceAdapter = new ConversionServiceAdapter(conversionService);

        Schedule schedule = new Schedule();
        ScheduleResponse expectedResponse = new ScheduleResponse();

        when(conversionService.convert(schedule, TypeDescriptor.valueOf(Schedule.class), TypeDescriptor.valueOf(ScheduleResponse.class)))
                .thenReturn(expectedResponse);

        ScheduleResponse actualResponse = conversionServiceAdapter.mapScheduleToScheduleResponse(schedule);

        assertEquals(expectedResponse, actualResponse);
    }
}
