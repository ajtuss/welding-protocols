package pl.coderslab.domain.services;

import pl.coderslab.domain.entities.WelderModel;

import java.util.List;

public interface WelderModelService {

    WelderModel findById(Long id);
    List<WelderModel> findAll();
    void save(WelderModel model);
    void update(Long id, WelderModel model);
}
