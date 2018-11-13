package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.coderslab.service.dto.*;
import pl.coderslab.domain.DBFile;

import java.util.Optional;

public interface ValidProtocolService {

    Page<ValidProtocolDTO> findAll(Pageable pageable);

    Optional<ValidProtocolDTO> findById(long id);

    ValidProtocolDTO save(ValidProtocolDTO validProtocolDTO);

    void remove(long id);

    Page<MeasureDTO> findAllMeasures(long id, Pageable pageable);

    Optional<ValidProtocolDTO> closeProtocol(long id);

    Optional<ValidProtocolDTO> openProtocol(long id);

    DBFile getFile(long id);

}
