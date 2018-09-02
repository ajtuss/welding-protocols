package pl.coderslab.domain.services;


import pl.coderslab.domain.dto.ParameterDto;
import pl.coderslab.domain.entities.Parameter;

import java.util.List;

public interface ParameterService {

    ParameterDto findById(Long id);

}
