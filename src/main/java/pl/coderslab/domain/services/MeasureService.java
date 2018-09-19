package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.entities.ValidProtocol;

import java.util.List;

public interface MeasureService {

    void generateMeasure(ValidProtocol validProtocol);

    List<MeasureDTO> findAll();

    MeasureDTO findById(Long id);

    MeasureDTO save(MeasureDTO measureDTO);

    MeasureDTO update(MeasureDTO measureDTO);

    void remove(Long id);

    ValidProtocolDTO findProtocolByMeasureId();


}
