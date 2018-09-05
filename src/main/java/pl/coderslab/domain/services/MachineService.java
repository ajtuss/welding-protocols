package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.MachineDto;

import java.util.List;


public interface MachineService {

    void save(MachineDto machineDto);

    List<MachineDto> findAll();

    MachineDto findById(Long id);

    void update(Long id, MachineDto machineDto);

    void remove(Long id);
}
