package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.ValidProtocolDto;
import pl.coderslab.domain.entities.Machine;
import pl.coderslab.domain.entities.ValidProtocol;
import pl.coderslab.domain.repositories.MachineRepository;
import pl.coderslab.domain.repositories.ValidProtocolRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class ValidProtocolServiceImpl implements ValidProtocolService {

    private final ValidProtocolRepository validProtocolRepository;
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ValidProtocolServiceImpl(ValidProtocolRepository validProtocolRepository, ModelMapper modelMapper, MachineRepository machineRepository) {
        this.validProtocolRepository = validProtocolRepository;
        this.modelMapper = modelMapper;
        this.machineRepository = machineRepository;
    }


    @Override
    public List<ValidProtocolDto> findAll() {
        List<ValidProtocol> protocols = validProtocolRepository.findAll();
        Type resultType = new TypeToken<List<ValidProtocolDto>>() {
        }.getType();
        return modelMapper.map(protocols, resultType);
    }

    @Override
    public Long save(ValidProtocolDto validProtocolDto) {
        ValidProtocol protocol = getValidProtocol(validProtocolDto);
        ValidProtocol savedProt = validProtocolRepository.save(protocol);
        return savedProt.getId();
    }

    @Override
    public ValidProtocolDto findById(Long id) {
        ValidProtocol protocol = validProtocolRepository.findById(id).get();
        return modelMapper.map(protocol, ValidProtocolDto.class);
    }

    @Override
    public void update(Long id, ValidProtocolDto validProtocolDto) {
        ValidProtocol validProtocol = getValidProtocol(validProtocolDto);
        validProtocol.setId(id);
        validProtocolRepository.save(validProtocol);
    }

    @Override
    public void remove(Long id) {
        ValidProtocol protocol = validProtocolRepository.findById(id).get();
        validProtocolRepository.delete(protocol);
    }

    @Override
    public ValidProtocolDto getNewValidProtocol(Long machineId) {
        Machine machine = machineRepository.findById(machineId).get();
        ValidProtocol validProtocol = new ValidProtocol();
        validProtocol.setMachine(machine);
        return modelMapper.map(validProtocol, ValidProtocolDto.class);
    }

    private ValidProtocol getValidProtocol(ValidProtocolDto validDto) {
        Long machineId = validDto.getMachineId();
        ValidProtocol protocol = modelMapper.map(validDto, ValidProtocol.class);
        Machine machine = machineRepository.findById(machineId).orElse(null);
        protocol.setMachine(machine);
        return protocol;
    }
}
