package pl.coderslab.service;

import pl.coderslab.service.dto.MeasureDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.domain.ValidProtocol;

import java.util.List;

public interface MeasureService {

    void generateMeasure(ValidProtocol validProtocol);

    List<MeasureDTO> findAll();

    MeasureDTO findById(Long id);

    MeasureDTO save(MeasureDTO measureDTO);

    MeasureDTO update(MeasureDTO measureDTO);

    void remove(Long id);

    ValidProtocolDTO findProtocolByMeasureId(Long id);


}
