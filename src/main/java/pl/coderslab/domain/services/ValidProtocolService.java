package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;

import java.util.List;

public interface ValidProtocolService {

    List<ValidProtocolDTO> findAll();

    ValidProtocolDTO findById(Long id);

    ValidProtocolDTO save(ValidProtocolDTO validProtocolDTO);

    ValidProtocolDTO update(ValidProtocolDTO validProtocolDTO);

    void remove(Long id);

    List<MeasureDTO> findAllMeasures(Long id);

    MachineDTO findMachineByValidProtocolId(Long id);

    void closeProtocol(Long id);
    void openProtocol(Long id);
}
