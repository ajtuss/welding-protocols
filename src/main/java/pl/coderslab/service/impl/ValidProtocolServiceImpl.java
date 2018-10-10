package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.coderslab.domain.*;
import pl.coderslab.service.dto.*;
import pl.coderslab.web.errors.*;
import pl.coderslab.repository.*;
import pl.coderslab.service.MeasureService;
import pl.coderslab.service.ValidProtocolService;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class ValidProtocolServiceImpl implements ValidProtocolService {

    private final MeasureService measureService;
    private final ValidProtocolRepository validProtocolRepository;
    private final MachineRepository machineRepository;
    private final WelderModelRepository modelRepository;
    private final CustomerRepository customerRepository;
    private final MeasureRepository measureRepository;
    private final DBFileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ValidProtocolServiceImpl(MeasureService measureService, ValidProtocolRepository validProtocolRepository, ModelMapper modelMapper, MachineRepository machineRepository, WelderModelRepository modelRepository, CustomerRepository customerRepository, MeasureRepository measureRepository, DBFileRepository fileRepository) {
        this.measureService = measureService;
        this.validProtocolRepository = validProtocolRepository;
        this.modelMapper = modelMapper;
        this.machineRepository = machineRepository;
        this.modelRepository = modelRepository;
        this.customerRepository = customerRepository;
        this.measureRepository = measureRepository;
        this.fileRepository = fileRepository;
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
        Machine machine = getMachine(validProtocolDTO.getMachineId());
        protocol.setMachine(machine);
        WelderModel model = machine.getWelderModel();
        PowerType type = validProtocolDTO.getType();
        if ((type.equals(PowerType.MIG) && !model.getMig()) ||
                (type.equals(PowerType.MMA) && !model.getMma()) ||
                (type.equals(PowerType.TIG) && !model.getTig())) {
            throw new BadRequestException("Validation Type is not correct for this model", null, null);
        }
        protocol.setType(type);

        ValidProtocol saved = validProtocolRepository.save(protocol);
        measureService.generateMeasure(saved);

        return modelMapper.map(saved, ValidProtocolDTO.class);
    }

    @Override
    public ValidProtocolDTO findById(Long id) {
        ValidProtocol protocol = getProtocol(id);
        return modelMapper.map(protocol, ValidProtocolDTO.class);
    }

    @Override
    public ValidProtocolDTO update(ValidProtocolDTO validProtocolDTO) {
//        ValidProtocol validProtocol = getValidProtocol(validProtocolDTO);
//        ValidProtocol save = validProtocolRepository.save(validProtocol);
//        return modelMapper.map(save, ValidProtocolDTO.class);
        return null;
    }

    @Override
    public void remove(Long id) {
        ValidProtocol protocol = getProtocol(id);
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

    @Override
    public void closeProtocol(Long id) {
        ValidProtocol protocol = getProtocol(id);
        checkResult(protocol);
        protocol.setFinalized(true);
        generatePdf(protocol);
        validProtocolRepository.save(protocol);
    }

    private void generatePdf(ValidProtocol protocol) {
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <title>Bootstrap Example</title>\n" +
                "  <meta charset=\"utf-8\"></meta>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"></meta>\n" +
                "  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"></link>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"container\">\n" +
                "  <h2>Contextual Classes</h2>\n" +
                "  <p>Contextual classes can be used to color table rows or table cells. The classes that can be used are: .active, .success, .info, .warning, and .danger.</p>\n" +
                "  <table class=\"table table-bordered\">\n" +
                "    <thead>\n" +
                "      <tr>\n" +
                "        <th>Firstname</th>\n" +
                "        <th>Lastname</th>\n" +
                "        <th>Email</th>\n" +
                "      </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "      <tr>\n" +
                "        <td>Default</td>\n" +
                "        <td>Defaultson</td>\n" +
                "        <td>def@somemail.com</td>\n" +
                "      </tr>      \n" +
                "      <tr class=\"success\">\n" +
                "        <td>Success</td>\n" +
                "        <td>Doe</td>\n" +
                "        <td>john@example.com</td>\n" +
                "      </tr>\n" +
                "      <tr class=\"danger\">\n" +
                "        <td>Danger</td>\n" +
                "        <td>Moe</td>\n" +
                "        <td>mary@example.com</td>\n" +
                "      </tr>\n" +
                "      <tr class=\"info\">\n" +
                "        <td>Info</td>\n" +
                "        <td>Dooley</td>\n" +
                "        <td>july@example.com</td>\n" +
                "      </tr>\n" +
                "      <tr class=\"warning\">\n" +
                "        <td>Warning</td>\n" +
                "        <td>Refs</td>\n" +
                "        <td>bo@example.com</td>\n" +
                "      </tr>\n" +
                "      <tr class=\"active\">\n" +
                "        <td>Active</td>\n" +
                "        <td>Activeson</td>\n" +
                "        <td>act@example.com</td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n" +
                "\n";


        try {


            OutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.close();

            DBFile dbFile = new DBFile();
            dbFile.setFileName("content.pdf");
            dbFile.setFileType("application/pdf");
            dbFile.setData(((ByteArrayOutputStream) outputStream).toByteArray());
            protocol.setProtocol(dbFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new PdfGeneratingException();
        }

        System.out.println("PDF Created!");

    }

    @Override
    public void openProtocol(Long id) {
        ValidProtocol protocol = getProtocol(id);
        protocol.setFinalized(false);
        protocol.setResult(null);
        validProtocolRepository.save(protocol);
    }

    @Override
    public DBFile getFile(Long id) {
        DBFile protocol = getProtocol(id).getProtocol();
        if(protocol == null){
            throw new DBFileNotFoundException();
        }
        return protocol;
    }

    @Override
    public CustomerDTO findCustomerByValidProtocolId(Long id) {
        Customer customer = customerRepository.findByValidProtocolId(id);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public WelderModelDTO findWelderModelByValidProtocolId(Long id) {
        WelderModel model = modelRepository.findByValidProtocolId(id);
        return modelMapper.map(model, WelderModelDTO.class);
    }

    public void checkResult(ValidProtocol validProtocol) {
        boolean current = validProtocol.getMeasures().stream().allMatch(Measure::isIResult);
        boolean voltage = validProtocol.getMeasures().stream().allMatch(Measure::isUResult);
        WelderModel welderModel = validProtocol.getMachine().getWelderModel();
        if (welderModel.getVoltageMeter()) {
            validProtocol.setResult(current && voltage);
        }
        validProtocol.setResult(current);
    }

    private ValidProtocol getValidProtocol(ValidProtocolDTO validDto) {
        Long machineId = validDto.getMachineId();
        ValidProtocol protocol = modelMapper.map(validDto, ValidProtocol.class);
        Machine machine = getMachine(machineId);
        protocol.setMachine(machine);
        return protocol;
    }

    private ValidProtocol getProtocol(Long id) {
        return validProtocolRepository.findById(id)
                                      .orElseThrow(() -> new ValidProtocolNotFoundException(id));
    }

    private Machine getMachine(Long machineId) {
        return machineRepository.findById(machineId)
                                .orElseThrow(() -> new MachineNotFoundException(machineId));
    }
}
