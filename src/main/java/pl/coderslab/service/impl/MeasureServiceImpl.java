package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.*;
import pl.coderslab.repository.MeasureRepository;
import pl.coderslab.repository.ValidProtocolRepository;
import pl.coderslab.service.MeasureService;
import pl.coderslab.service.dto.MeasureDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.web.errors.BadRequestException;
import pl.coderslab.web.errors.MeasureNotFoundException;
import pl.coderslab.web.errors.ValidProtocolNotFoundException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

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
    public Page<MeasureDTO> findAll(Pageable pageable) {
        return measureRepository.findAll(pageable)
                                .map(measure -> mapper.map(measure, MeasureDTO.class));
    }

    @Override
    public Optional<MeasureDTO> findById(long id) {
        return measureRepository.findById(id)
                                .map(measure -> mapper.map(measure, MeasureDTO.class));
    }

    private Range getRange(ValidProtocol protocol) {
        PowerType type = protocol.getType();
        WelderModel model = protocol.getMachine().getWelderModel();
        Range range = null;
        switch (type) {
            case TIG:
                range = model.getTigRange();
                break;
            case MMA:
                range = model.getMmaRange();
                break;
            case MIG:
                range = model.getMigRange();
                break;
        }
        return range;
    }

    private BigDecimal calculateVoltage(BigDecimal current, ValidProtocol protocol) {
        PowerType type = protocol.getType();
        WelderModel model = protocol.getMachine().getWelderModel();
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
        BigDecimal resultVoltage = BigDecimal.valueOf(over).add(BigDecimal.valueOf(multiply).multiply(current));
        return checkVoltageRange(resultVoltage, range);
    }

    private BigDecimal checkVoltageRange(BigDecimal voltage, Range range) {
        if (voltage.compareTo(range.getUMax()) > 0) {
            return range.getUMax();
        }
        if (voltage.compareTo(range.getUMin()) < 0) {
            return range.getUMin();
        }
        return voltage;
    }

    private BigDecimal checkCurrentRange(BigDecimal current, Range range) {
        if (current.compareTo(range.getIMax()) > 0) {
            throw new BadRequestException("iAdjust is to high", null, null);
        }
        if (current.compareTo(range.getIMin()) < 0) {
            throw new BadRequestException("iAdjust is to low", null, null);
        }
        return current;
    }

    @Override
    public void generateMeasure(ValidProtocol validProtocol) {
        Range range = getRange(validProtocol);
        BigDecimal step = (range.getIMax().subtract(range.getIMin())).divide(BigDecimal.valueOf(4));
        BigDecimal current = range.getIMin();
        for (int i = 0; i < 5; i++) {
            Measure measure = new Measure();
            measure.setIAdjust(current);
            measure.setUAdjust(calculateVoltage(current, validProtocol));
            validProtocol.addMeasure(measure);
            current = current.add(step);
        }
    }

    @Override
    public MeasureDTO save(MeasureDTO measureDTO) {
        Measure measure = new Measure();
        ValidProtocol protocol = getProtocol(measureDTO.getValidProtocolId());
        measure.setValidProtocol(protocol);
        BigDecimal current = checkCurrentRange(measureDTO.getIAdjust(), getRange(protocol));
        measure.setIAdjust(current);
        measure.setUAdjust(calculateVoltage(current, protocol));
        Measure save = measureRepository.save(measure);
        return mapper.map(save, MeasureDTO.class);
    }

    @Override
    public MeasureDTO update(MeasureDTO measureDTO) {
        if (measureDTO.getId() == null || measureDTO.getVersionId() == null) {
            throw new BadRequestException("To update id and versionId can`t be null", null, null);

        }
        Measure measure = measureRepository.findById(measureDTO.getId())
                                           .orElseThrow(() -> new MeasureNotFoundException(measureDTO.getId()));
        ValidProtocol validProtocol = measure.getValidProtocol();

        if (validProtocol.isFinalized()) {
            throw new BadRequestException("Protocol is closed, Cant change measure", null, null);
        }
        measure.setIPower(measureDTO.getIPower());
        measure.setUPower(measureDTO.getUPower());
        measure.setIValid(measureDTO.getIValid());
        measure.setUValid(measureDTO.getUValid());

        Range range = getRange(validProtocol);

        if (measure.getIPower() != null && measure.getIValid() != null) {
            BigDecimal diff = measure.getIPower().subtract(measure.getIValid());
            measure.setIError(diff);
            BigDecimal maxIError = calculateMaxIError(range, measure.getIAdjust());
            System.out.println(maxIError);
            measure.setIResult(diff.abs().compareTo(maxIError) < 1);
        }
        if (measure.getUAdjust() != null && measure.getUPower() != null && measure.getUValid() != null) {
            BigDecimal diff = measure.getUPower().subtract(measure.getUValid());
            measure.setUError(diff);
            BigDecimal maxUError = calculateMaxUError(range, measure.getUAdjust());
            System.out.println(maxUError);
            measure.setUResult(diff.abs().compareTo(maxUError) < 1);
        }
        Measure save = measureRepository.saveAndFlush(measure);
        entityManager.refresh(save);
        return mapper.map(save, MeasureDTO.class);
    }

    @Override
    public void remove(long id) {
        measureRepository.findById(id)
                         .ifPresent(measureRepository::delete);
    }

    @Override
    public Page<ValidProtocolDTO> findProtocolByMeasureId(long id, Pageable pageable) {
        return null; //todo
    }

    private BigDecimal calculateMaxIError(Range range, BigDecimal iAdjust) {
        BigDecimal diff = range.getIMax().subtract(range.getIMin());
        BigDecimal quarter = diff.divide(BigDecimal.valueOf(4));
        BigDecimal firstQuarter = quarter.add(range.getIMin());
        if (iAdjust.compareTo(firstQuarter) < 1) {
            return range.getIMax().multiply(BigDecimal.valueOf(0.05));
        }
        return iAdjust.multiply(BigDecimal.valueOf(0.05));
    }

    private BigDecimal calculateMaxUError(Range range, BigDecimal uAdjust) {
        BigDecimal diff = range.getUMax().subtract(range.getUMin());
        BigDecimal quarter = diff.divide(BigDecimal.valueOf(4));
        BigDecimal firstQuarter = quarter.add(range.getUMin());
        if (uAdjust.compareTo(firstQuarter) < 1) {
            return range.getUMax().multiply(BigDecimal.valueOf(0.05));
        }
        return uAdjust.multiply(BigDecimal.valueOf(0.05));
    }

    private ValidProtocol getProtocol(long id) {
        return protocolRepository.findById(id)
                                 .orElseThrow(() -> new ValidProtocolNotFoundException(id));
    }
}
