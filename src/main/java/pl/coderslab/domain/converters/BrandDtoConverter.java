package pl.coderslab.domain.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.services.BrandService;

@Component
public class BrandDtoConverter implements Converter<String, BrandDto> {

    @Autowired
    private BrandService brandService;

    @Override
    public BrandDto convert(String brandId) {
        return brandService.findById(Long.parseLong(brandId));
    }
}
