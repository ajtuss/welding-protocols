package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.entities.*;
import pl.coderslab.domain.exceptions.InvalidRequestException;
import pl.coderslab.domain.exceptions.MeasureNotFoundException;
import pl.coderslab.domain.exceptions.ValidProtocolNotFoundException;
import pl.coderslab.domain.repositories.MeasureRepository;
import pl.coderslab.domain.repositories.ValidProtocolRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class MeasureServiceImpl implements MeasureService {

    private final MeasureRepository measureRepository;
    private final ValidProtocolRepository protocolRepository;
    private final EntityManager entityManager;
    private final ModelMapper mapper;

    @Autowired
    public MeasureServiceImpl(MeasureRepository measureRepository, ValidProtocolRepository protocolRepository, EntityManager entityManager, ModelMapper mapper) {
        this.measureRepository = measureRepository;
        this.protocolRepository = protocolRepository;
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    @Override
    public void generateMeasure(ValidProtocol validProtocol) {
        PowerType type = validProtocol.getType();
        WelderModel model = validProtocol.getMachine().getWelderModel();
        Range range = null;
        int over = 0;
        double multiply = 0;
        switch (type) {
            case TIG:
                range = model.getTigRange();
                over = 10;
                multiply = 0.04;
                break;
            case MMA:
                range = model.getMmaRange();
                over = 20;
                multiply = 0.04;
                break;
            case MIG:
                range = model.getMigRange();
                over = 14;
                multiply = 0.05;
                break;
        }
        BigDecimal step = (range.getIMax().subtract(range.getIMin())).divide(BigDecimal.valueOf(4));
        BigDecimal current = range.getIMin();
        for (int i = 0; i < 5; i++) {
            Measure measure = new Measure();
            measure.setIAdjust(current);
            measure.setUAdjust(BigDecimal.valueOf(over).add(BigDecimal.valueOf(multiply).multiply(current)));
            validProtocol.addMeasure(measure);
            current = current.add(step);
        }
    }

    @Override
    public List<MeasureDTO> findAll() {
        List<Measure> measures = measureRepository.findAll();
        Type resultType = new TypeToken<List<MeasureDTO>>() {
        }.getType();
        return mapper.map(measures, resultType);
    }

    @Override
    public MeasureDTO findById(Long id) {
        Measure measure = measureRepository.findById(id).orElseThrow(() -> new MeasureNotFoundException(id));
        return mapper.map(measure, MeasureDTO.class);
    }

    @Override
    public MeasureDTO save(MeasureDTO measureDTO) {
        Measure measure = mapper.map(measureDTO, Measure.class);
        Long protocolId = measureDTO.getValidProtocolId();
        ValidProtocol validProtocol = protocolRepository.findById(protocolId)
                                                        .orElseThrow(() -> new ValidProtocolNotFoundException(protocolId));
        measure.setValidProtocol(validProtocol);
        Measure save = measureRepository.save(measure);
        return mapper.map(save, MeasureDTO.class);
    }

    @Override
    public MeasureDTO update(MeasureDTO measureDTO) {
        if(measureDTO.getId() == null || measureDTO.getVersionId() == null){
            throw new InvalidRequestException("To update id and versionId can`t be null");

        }
        Measure measure = mapper.map(measureDTO, Measure.class);
        Long protocolId = measureDTO.getValidProtocolId();
        ValidProtocol validProtocol = protocolRepository.findById(protocolId)
                                                        .orElseThrow(() -> new ValidProtocolNotFoundException(protocolId));
        if(validProtocol.isFinalized()){
            throw new InvalidRequestException("Protocol is closed, Cant change measure");
        }
        measure.setValidProtocol(validProtocol);
        Measure save = measureRepository.saveAndFlush(measure);
        entityManager.refresh(save);
        return mapper.map(save, MeasureDTO.class);
    }

    @Override
    public void remove(Long id) {
        Measure measure = measureRepository.findById(id).orElseThrow(() -> new MeasureNotFoundException(id));
        measureRepository.delete(measure);
    }

    @Override
    public ValidProtocolDTO findProtocolByMeasureId(Long id) {
        return null;
    }
}
