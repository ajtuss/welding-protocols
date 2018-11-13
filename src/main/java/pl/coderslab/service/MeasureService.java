package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.coderslab.service.dto.MeasureDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.domain.ValidProtocol;

import java.util.Optional;

public interface MeasureService {

    Page<MeasureDTO> findAll(Pageable pageable);

    Optional<MeasureDTO> findById(long id);

    MeasureDTO save(MeasureDTO measureDTO);

    MeasureDTO update(MeasureDTO measureDTO);

    void remove(long id);

    Page<ValidProtocolDTO> findProtocolByMeasureId(long id, Pageable pageable);

    void generateMeasure(ValidProtocol validProtocol);


}
