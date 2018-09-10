package pl.coderslab.domain.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.WelderModelService;

@Component
public class WelderModelDtoConverter implements Converter<String,WelderModelDTO> {

    @Autowired
    private WelderModelService modelService;

    @Override
    public WelderModelDTO convert(String modelId) {
        return modelService.findById(Long.parseLong(modelId));
    }
}
