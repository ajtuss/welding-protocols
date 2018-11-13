package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.coderslab.domain.DBFile;
import pl.coderslab.domain.Measure;
import pl.coderslab.domain.ValidProtocol;
import pl.coderslab.domain.WelderModel;
import pl.coderslab.repository.DBFileRepository;
import pl.coderslab.repository.MachineRepository;
import pl.coderslab.repository.MeasureRepository;
import pl.coderslab.repository.ValidProtocolRepository;
import pl.coderslab.service.MeasureService;
import pl.coderslab.service.ValidProtocolService;
import pl.coderslab.service.dto.MeasureDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.web.errors.BadRequestException;
import pl.coderslab.web.errors.ErrorConstants;
import pl.coderslab.web.errors.PdfGeneratingException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Optional;

@Service
@Transactional
public class ValidProtocolServiceImpl implements ValidProtocolService {

    private static final String ENTITY_NAME = "validation";

    private final MeasureService measureService;
    private final ValidProtocolRepository validProtocolRepository;
    private final MachineRepository machineRepository;
    private final MeasureRepository measureRepository;
    private final DBFileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ValidProtocolServiceImpl(MeasureService measureService, ValidProtocolRepository validProtocolRepository, ModelMapper modelMapper, MachineRepository machineRepository, MeasureRepository measureRepository, DBFileRepository fileRepository) {
        this.measureService = measureService;
        this.validProtocolRepository = validProtocolRepository;
        this.modelMapper = modelMapper;
        this.machineRepository = machineRepository;
        this.measureRepository = measureRepository;
        this.fileRepository = fileRepository;
    }


    @Override
    public Page<ValidProtocolDTO> findAll(Pageable pageable) {
        return validProtocolRepository.findAll(pageable)
                                      .map(vp -> modelMapper.map(vp, ValidProtocolDTO.class));
    }

    @Override
    public ValidProtocolDTO save(ValidProtocolDTO validProtocolDTO) {

        ValidProtocol validProtocol = modelMapper.map(validProtocolDTO, ValidProtocol.class);

        machineRepository.findById(validProtocolDTO.getMachineId())
                         .ifPresent(validProtocol::setMachine);

        ValidProtocol saved = validProtocolRepository.save(validProtocol);
        measureService.generateMeasure(saved);
        return modelMapper.map(saved, ValidProtocolDTO.class);

    }

    @Override
    public Optional<ValidProtocolDTO> findById(long id) {
        return validProtocolRepository.findById(id)
                                      .map(vp -> modelMapper.map(vp, ValidProtocolDTO.class));
    }

    @Override
    public void remove(long id) {
        validProtocolRepository.findById(id)
                               .ifPresent(validProtocolRepository::delete);
    }

    @Override
    public Page<MeasureDTO> findAllMeasures(long protocolId, Pageable pageable) {
        return measureRepository.findByValidProtocolId(protocolId, pageable)
                                .map(measure -> modelMapper.map(measure, MeasureDTO.class));
    }

    @Override
    public Optional<ValidProtocolDTO> closeProtocol(long id) {

        return validProtocolRepository.findById(id)
                                      .map(validProtocol -> {
                                          if (validProtocol.isFinalized()) {
                                              throw new BadRequestException("Protocol is closed", ENTITY_NAME, ErrorConstants.ERR_PROTOCOL_IS_CLOSED);
                                          }
                                          checkResult(validProtocol);
                                          validProtocol.setFinalized(true);
                                          generatePdf(validProtocol);
                                          return validProtocolRepository.save(validProtocol);
                                      }).map(validProtocol -> modelMapper.map(validProtocol, ValidProtocolDTO.class));
    }

    @Override
    public Optional<ValidProtocolDTO> openProtocol(long id) {

        return validProtocolRepository.findById(id)
                                      .map(validProtocol -> {
                                          if (!validProtocol.isFinalized()) {
                                              throw new BadRequestException("Protocol is open", ENTITY_NAME, ErrorConstants.ERR_PROTOCOL_IS_OPEN);
                                          }
                                          validProtocol.setFinalized(false);
                                          validProtocol.setResult(null);
                                          return validProtocolRepository.save(validProtocol);
                                      }).map(validProtocol -> modelMapper.map(validProtocol, ValidProtocolDTO.class));
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
    public DBFile getFile(long id) {
//        DBFile protocol = getProtocol(id).getProtocol();
//        if (protocol == null) {
//            throw new DBFileNotFoundException();
//        }
//        return protocol;
        throw new NotImplementedException();
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

//    private ValidProtocol getValidProtocol(ValidProtocolDTO validDto) {
//        Long machineId = validDto.getMachineId();
//        ValidProtocol protocol = modelMapper.map(validDto, ValidProtocol.class);
//        Machine machine = getMachine(machineId);
//        protocol.setMachine(machine);
//        return protocol;
//    }
//
//    private Optional<ValidProtocol> getProtocol(Long id) {
//        return validProtocolRepository.findById(id)
//                                      .orElseThrow(() -> new ValidProtocolNotFoundException(id));
//    }
//
//    private Machine getMachine(Long machineId) {
//        return machineRepository.findById(machineId)
//                                .orElseThrow(() -> new MachineNotFoundException(machineId));
//    }
}
