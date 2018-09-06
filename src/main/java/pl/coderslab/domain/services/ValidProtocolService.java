package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.MeasureDto;
import pl.coderslab.domain.dto.ValidProtocolDto;

import java.util.List;

public interface ValidProtocolService {

    List<ValidProtocolDto> findAll();

    Long save(ValidProtocolDto validProtocolDto);

    ValidProtocolDto findById(Long id);

    void update(Long id, ValidProtocolDto validProtocolDto);

    void remove(Long id);

    ValidProtocolDto getNewValidProtocol(Long machineId);

    List<MeasureDto> findAllMeasures(Long id);
}
