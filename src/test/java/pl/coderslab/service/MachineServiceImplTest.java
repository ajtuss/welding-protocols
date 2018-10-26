package pl.coderslab.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.repository.MachineRepository;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.impl.MachineServiceImpl;
import pl.coderslab.web.errors.MachineNotFoundException;

import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({MachineServiceImpl.class, ModelMapper.class})
@Sql(value = "/data-brands.sql")
@Sql(value = "/data-machines.sql")
public class MachineServiceImplTest {

    @Autowired
    private MachineService machineService;

    @Autowired
    private MachineRepository machineRepository;

    private static final MachineDTO MACHINE_1 = MachineDTO.builder()
                                                          .welderModelId(2L)
                                                          .customerId(1L)
                                                          .serialNumber("12345678")
                                                          .serialNumber("474-12312")
                                                          .build();
    private static final MachineDTO MACHINE_2 = MachineDTO.builder()
                                                          .welderModelId(3L)
                                                          .customerId(1L)
                                                          .serialNumber("876543322")
                                                          .serialNumber("474-123123")
                                                          .build();

    @Test
    public void expectedTrueAfterSaveMachine() {
        MachineDTO saved = machineService.save(MACHINE_1);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertThat(saved.getSerialNumber(), is(MACHINE_1.getSerialNumber()));
        assertThat(saved.getInwNumber(), is(MACHINE_1.getInwNumber()));
        assertNotNull(saved.getCreationDate());
        assertNotNull(saved.getModificationDate());
        assertNotNull(saved.getVersionId());
    }

    @Test
    public void expectedTrueAfterFindById() {
        MachineDTO save = machineService.save(MACHINE_1);

        Optional<MachineDTO> found = machineService.findById(save.getId());

        assertTrue(found.isPresent());
        assertThat(found.get(), is(save));
    }

    @Test(expected = MachineNotFoundException.class)
    public void expectedExceptionAfterFindNotExistingMachine() {
        machineService.findById(Long.MAX_VALUE);
    }

    @Test
    public void expectedTrueAfterFindAll() {
        MachineDTO machine1 = machineService.save(MACHINE_1);
        MachineDTO machine2 = machineService.save(MACHINE_2);

        Page<MachineDTO> found = machineService.findAll(new PageRequest(1, 2));

        assertThat(found.getContent(), containsInAnyOrder(machine1, machine2));
    }


    @Test
    public void expectedTrueAfterRemove() {
        MachineDTO save = machineService.save(MACHINE_1);
        machineService.remove(save.getId());
    }


}