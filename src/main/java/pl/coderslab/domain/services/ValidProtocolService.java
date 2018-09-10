package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;

import java.util.List;

public interface ValidProtocolService {

    List<ValidProtocolDTO> findAll();

    Long save(ValidProtocolDTO validProtocolDTO);

    ValidProtocolDTO findById(Long id);

    void update(Long id, ValidProtocolDTO validProtocolDTO);

    void remove(Long id);

    ValidProtocolDTO getNewValidProtocol(Long machineId);

    List<MeasureDTO> findAllMeasures(Long id);
}
