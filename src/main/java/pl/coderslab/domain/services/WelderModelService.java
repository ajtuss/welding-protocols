package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.dto.WelderModelUpdateDTO;

import java.util.List;

public interface WelderModelService {

    WelderModelDTO findById(Long id);
    List<WelderModelDTO> findAll();
    void save(WelderModelDTO model);
    WelderModelDTO update(WelderModelUpdateDTO model);

    void remove(Long id);
}
