package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.RangeDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.entities.*;
import pl.coderslab.domain.exceptions.InvalidRequestException;
import pl.coderslab.domain.exceptions.MachineNotFoundException;
import pl.coderslab.domain.exceptions.ValidProtocolNotFoundException;
import pl.coderslab.domain.repositories.MachineRepository;
import pl.coderslab.domain.repositories.MeasureRepository;
import pl.coderslab.domain.repositories.ValidProtocolRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ValidProtocolServiceImpl implements ValidProtocolService {

    private final ValidProtocolRepository validProtocolRepository;
    private final MachineRepository machineRepository;
    private final WelderModelRepository modelRepository;
    private final MeasureRepository measureRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ValidProtocolServiceImpl(ValidProtocolRepository validProtocolRepository, ModelMapper modelMapper, MachineRepository machineRepository, WelderModelRepository modelRepository, MeasureRepository measureRepository) {
        this.validProtocolRepository = validProtocolRepository;
        this.modelMapper = modelMapper;
        this.machineRepository = machineRepository;
        this.modelRepository = modelRepository;
        this.measureRepository = measureRepository;
    }


    @Override
    public List<ValidProtocolDTO> findAll() {
        List<ValidProtocol> protocols = validProtocolRepository.findAll();
        Type resultType = new TypeToken<List<ValidProtocolDTO>>() {
        }.getType();
        return modelMapper.map(protocols, resultType);
    }

    @Override
    public ValidProtocolDTO save(ValidProtocolDTO validProtocolDTO) {
        ValidProtocol protocol = new ValidProtocol();
        Machine machine = machineRepository.findById(validProtocolDTO.getMachineId())
                                           .orElseThrow(() -> new MachineNotFoundException(validProtocolDTO.getMachineId()));
        protocol.setMachine(machine);
        WelderModel model = machine.getWelderModel();
        PowerType type = validProtocolDTO.getType();
        if ((type.equals(PowerType.MIG) && !model.getMig()) ||
                (type.equals(PowerType.MMA) && !model.getMma()) ||
                (type.equals(PowerType.TIG) && !model.getTig())) {
            throw new InvalidRequestException("Validation Type is not correct for this model");
        }
        protocol.setType(type);

        List<Measure> measures = generateMeasures(model, type);
        measures.forEach(measure -> protocol.addMeasure(measure));
        ValidProtocol saved = validProtocolRepository.save(protocol);

        return modelMapper.map(saved, ValidProtocolDTO.class);
    }

    @Override
    public ValidProtocolDTO findById(Long id) {
        ValidProtocol protocol = validProtocolRepository.findById(id)
                                                        .orElseThrow(() -> new ValidProtocolNotFoundException(id));
        return modelMapper.map(protocol, ValidProtocolDTO.class);
    }

    @Override
    public ValidProtocolDTO update(ValidProtocolDTO validProtocolDTO) {
        ValidProtocol validProtocol = getValidProtocol(validProtocolDTO);
        ValidProtocol save = validProtocolRepository.save(validProtocol);
        return modelMapper.map(save, ValidProtocolDTO.class);
    }

    @Override
    public void remove(Long id) {
        ValidProtocol protocol = validProtocolRepository.findById(id)
                                                        .orElseThrow(() -> new ValidProtocolNotFoundException(id));
        validProtocolRepository.delete(protocol);
    }

    @Override
    public List<MeasureDTO> findAllMeasures(Long protocolId) {
        List<Measure> measures = measureRepository.findByValidProtocolId(protocolId);
        Type resultType = new TypeToken<List<MeasureDTO>>() {
        }.getType();
        return modelMapper.map(measures, resultType);
    }

    @Override
    public MachineDTO findMachineByValidProtocolId(Long id) {
        Machine machine = machineRepository.findByValidProtocolId(id);
        return modelMapper.map(machine, MachineDTO.class);
    }

    private ValidProtocol getValidProtocol(ValidProtocolDTO validDto) {
        Long machineId = validDto.getMachineId();
        ValidProtocol protocol = modelMapper.map(validDto, ValidProtocol.class);
        Machine machine = machineRepository.findById(machineId)
                                           .orElseThrow(() -> new MachineNotFoundException((machineId)));
        protocol.setMachine(machine);
        return protocol;
    }

    private List<Measure> generateMeasures(WelderModel model, PowerType type) {
        Range range = null;
        int over = 0;
        double multiply = 0;
        switch (type){
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
        List<Measure> result = new ArrayList<>();
        Double step = (range.getIMax() - range.getIMin())/4;
        Double current = range.getIMin();
        for (int i = 0; i < 5; i++) {
            Measure measure = new Measure();
            measure.setIAdjust(BigDecimal.valueOf(current));
            measure.setUAdjust(BigDecimal.valueOf(over + (multiply * current)));
            result.add(measure);
            current += step;
        }
        return result;
    }
}
