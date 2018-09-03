package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.WelderModelDto;

import java.util.List;

public interface WelderModelService {

    WelderModelDto findById(Long id);
    List<WelderModelDto> findAll();
    void save(WelderModelDto model);
    void update(Long id, WelderModelDto model);
}
