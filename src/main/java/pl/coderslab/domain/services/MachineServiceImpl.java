package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.MachineDto;
import pl.coderslab.domain.entities.Machine;
import pl.coderslab.domain.repositories.MachineRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, ModelMapper modelMapper) {
        this.machineRepository = machineRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void save(MachineDto machineDto) {
        Machine machine = modelMapper.map(machineDto, Machine.class);
        machineRepository.save(machine);
    }

    @Override
    public List<MachineDto> findAll() {
        List<Machine> machines = machineRepository.findAll();
        Type resultType = new TypeToken<List<MachineDto>>() {}.getType();
        return modelMapper.map(machines, resultType);
    }

    @Override
    public MachineDto findById(Long id) {
        Machine machine = machineRepository.findById(id).orElse(null);
        return modelMapper.map(machine, MachineDto.class);
    }

    @Override
    public void update(Long id, MachineDto machineDto) {
        Machine machine = modelMapper.map(machineDto, Machine.class);
        machine.setId(id);
        machineRepository.save(machine);
    }
}
