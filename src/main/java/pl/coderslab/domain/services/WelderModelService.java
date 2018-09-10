package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.WelderModelDTO;

import java.util.List;

public interface WelderModelService {

    WelderModelDTO findById(Long id);
    List<WelderModelDTO> findAll();
    void save(WelderModelDTO model);
    void update(Long id, WelderModelDTO model);

    void remove(Long id);
}
