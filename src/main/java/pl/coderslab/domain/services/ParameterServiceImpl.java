package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.ParameterDto;
import pl.coderslab.domain.entities.Parameter;
import pl.coderslab.domain.repositories.ParameterRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ParameterServiceImpl(ParameterRepository parameterRepository, ModelMapper modelMapper) {
        this.parameterRepository = parameterRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ParameterDto findById(Long id) {
        Parameter parameter = parameterRepository.findById(id).orElse(null);
        return modelMapper.map(parameter, ParameterDto.class);
    }
}
