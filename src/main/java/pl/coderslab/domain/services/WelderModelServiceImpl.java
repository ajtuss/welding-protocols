package pl.coderslab.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.entities.WelderModel;
import pl.coderslab.domain.repositories.WelderModelRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class WelderModelServiceImpl implements WelderModelService {

    private final WelderModelRepository modelRepository;

    @Autowired
    public WelderModelServiceImpl(WelderModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public WelderModel findById(Long id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public List<WelderModel> findAll() {
        return modelRepository.findAll();
    }

    @Override
    public void save(WelderModel model) {
        modelRepository.save(model);
    }

    @Override
    public void update(Long id, WelderModel model) {

    }
}
