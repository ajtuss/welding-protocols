package pl.coderslab.domain.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.coderslab.domain.dto.ParameterDto;
import pl.coderslab.domain.services.ParameterService;

@Component
public class ParameterDtoConverter implements Converter<String, ParameterDto> {

    @Autowired
    private ParameterService parameterService;

    @Override
    public ParameterDto convert(String parameterId) {
        return parameterService.findById(Long.parseLong(parameterId));
    }
}
