package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.*;
import pl.coderslab.domain.entities.DBFile;

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

    DBFile getFile(Long id);

    CustomerDTO findCustomerByValidProtocolId(Long id);

    WelderModelDTO findWelderModelByValidProtocolId(Long id);
}
