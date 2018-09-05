package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.ValidProtocolDto;

import java.util.List;

public interface ValidProtocolService {

    List<ValidProtocolDto> findAll();

    void save(ValidProtocolDto validProtocolDto);

    ValidProtocolDto findById(Long id);

    void update(Long id, ValidProtocolDto validProtocolDto);

    void remove(Long id);

    ValidProtocolDto getNewValidProtocol(Long machineId);
}
