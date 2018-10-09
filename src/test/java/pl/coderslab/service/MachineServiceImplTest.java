package pl.coderslab.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.web.exceptions.MachineNotFoundException;
import pl.coderslab.repository.MachineRepository;
import pl.coderslab.service.impl.MachineServiceImpl;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

        MachineDTO found = machineService.findById(save.getId());

        assertThat(found, is(save));
    }

    @Test(expected = MachineNotFoundException.class)
    public void expectedExceptionAfterFindNotExistingMachine() {
        machineService.findById(Long.MAX_VALUE);
    }

    @Test
    public void expectedTrueAfterFindAll() {
        MachineDTO machine1 = machineService.save(MACHINE_1);
        MachineDTO machine2 = machineService.save(MACHINE_2);

        List<MachineDTO> found = machineService.findAll();

        assertThat(found, containsInAnyOrder(machine1, machine2));
    }

    @Test
    public void expectedTrueAfterUpdate() {
        MachineDTO saved = machineService.save(MACHINE_1);
        saved.setSerialNumber("12345");
        MachineDTO updated = machineService.update(saved);

        assertNotNull(updated);
        assertThat(updated.getId(), is(saved.getId()));
        assertThat(updated.getSerialNumber(), is(saved.getSerialNumber()));
        assertThat(updated.getInwNumber(), is(saved.getInwNumber()));
        assertThat(updated.getCreationDate(), is(saved.getCreationDate()));
        assertTrue(updated.getModificationDate().isAfter(saved.getModificationDate()));
        assertTrue(updated.getVersionId() > saved.getVersionId());
    }

    @Test
    public void expectedTrueAfterRemove() {
        MachineDTO save = machineService.save(MACHINE_1);
        machineService.remove(save.getId());
    }

    @Test(expected = MachineNotFoundException.class)
    public void expectedExceptionAfterRemoveAndFind() {
        MachineDTO save = machineService.save(MACHINE_1);
        machineService.remove(save.getId());
        machineService.findById(save.getId());
    }

    @Test
    public void expectedTrueAfterFindCustomer() {
        MachineDTO save = machineService.save(MACHINE_1);
        CustomerDTO customer = machineService.findCustomerByMachineId(save.getId());
        assertNotNull(customer);
    }

    @Test
    public void expectedTrueAfterFindModel() {
        MachineDTO save = machineService.save(MACHINE_1);
        WelderModelDTO model = machineService.findModelByMachineId(save.getId());
        System.out.println(model);
        assertNotNull(model);
    }


}