package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.entities.*;
import pl.coderslab.domain.repositories.MachineRepository;
import pl.coderslab.domain.repositories.MeasureRepository;
import pl.coderslab.domain.repositories.ValidProtocolRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
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
    public Long save(ValidProtocolDTO validProtocolDTO) {
        System.out.println(validProtocolDTO.getMachineId());
        Boolean auto = validProtocolDTO.getAuto();
        ValidProtocol protocol = getValidProtocol(validProtocolDTO);
        if (auto) {
            WelderModel welderModel = protocol.getMachine().getWelderModel();
            Double iMin = null;
            Double uMin = null;
            Double iMax = null;
            Double uMax = null;
            PowerType type = protocol.getType();
            switch (type) {
                case MIG:
                    iMin = welderModel.getMigImin();
                    uMin = welderModel.getMigUmin();
                    iMax = welderModel.getMigImax();
                    uMax = welderModel.getMigUmax();
                    break;
                case MMA:
                    iMin = welderModel.getMmaImin();
                    uMin = welderModel.getMmaUmin();
                    iMax = welderModel.getMmaImax();
                    uMax = welderModel.getMmaUmax();
                    break;
                case TIG:
                    iMin = welderModel.getTigImin();
                    uMin = welderModel.getTigUmin();
                    iMax = welderModel.getTigImax();
                    uMax = welderModel.getTigUmax();
                    break;
            }
            Measure measure1 = new Measure();
            measure1.setiAdjust(iMin);
            measure1.setuAdjust(uMin);

            Measure measure2 = new Measure();
            measure2.setiAdjust(iMin);
            measure2.setuAdjust(uMin);


            Measure measure3 = new Measure();
            measure3.setiAdjust((int) ((iMax-iMin)/2)+iMin);
            measure3.setuAdjust((int) ((uMax - uMin)/2)+uMin);


            Measure measure4 = new Measure();
            measure4.setiAdjust(iMin);
            measure4.setuAdjust(uMin);


            Measure measure5 = new Measure();
            measure5.setiAdjust(iMax);
            measure5.setuAdjust(uMax);

            protocol.addMeasure(measure1);
            protocol.addMeasure(measure2);
            protocol.addMeasure(measure3);
            protocol.addMeasure(measure4);
            protocol.addMeasure(measure5);
        }
        ValidProtocol savedProt = validProtocolRepository.save(protocol);
        return savedProt.getId();
    }

    @Override
    public ValidProtocolDTO findById(Long id) {
        ValidProtocol protocol = validProtocolRepository.findById(id).get();
        return modelMapper.map(protocol, ValidProtocolDTO.class);
    }

    @Override
    public void update(Long id, ValidProtocolDTO validProtocolDTO) {
        ValidProtocol validProtocol = getValidProtocol(validProtocolDTO);
        validProtocol.setId(id);
        validProtocolRepository.save(validProtocol);
    }

    @Override
    public void remove(Long id) {
        ValidProtocol protocol = validProtocolRepository.findById(id).get();
        validProtocolRepository.delete(protocol);
    }

    @Override
    public ValidProtocolDTO getNewValidProtocol(Long machineId) {
        Machine machine = machineRepository.findById(machineId).get();
        ValidProtocol validProtocol = new ValidProtocol();
        validProtocol.setMachine(machine);
        return modelMapper.map(validProtocol, ValidProtocolDTO.class);
    }

    @Override
    public List<MeasureDTO> findAllMeasures(Long protocolId) {
        List<Measure> measures = measureRepository.findByValidProtocolId(protocolId);
        measures.forEach(System.out::println);
        Type resultType = new TypeToken<List<MeasureDTO>>() {
        }.getType();
        return modelMapper.map(measures, resultType);
    }

    private ValidProtocol getValidProtocol(ValidProtocolDTO validDto) {
        Long machineId = validDto.getMachineId();
        ValidProtocol protocol = modelMapper.map(validDto, ValidProtocol.class);
        Machine machine = machineRepository.findById(machineId).orElse(null);
        protocol.setMachine(machine);
        return protocol;
    }
}
