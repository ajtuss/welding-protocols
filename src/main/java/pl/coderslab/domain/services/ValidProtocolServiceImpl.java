package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.entities.*;
import pl.coderslab.domain.exceptions.*;
import pl.coderslab.domain.repositories.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import javax.transaction.Transactional;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
public class ValidProtocolServiceImpl implements ValidProtocolService {

    private final MeasureService measureService;
    private final ValidProtocolRepository validProtocolRepository;
    private final MachineRepository machineRepository;
    private final WelderModelRepository modelRepository;
    private final MeasureRepository measureRepository;
    private final DBFileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ValidProtocolServiceImpl(MeasureService measureService, ValidProtocolRepository validProtocolRepository, ModelMapper modelMapper, MachineRepository machineRepository, WelderModelRepository modelRepository, MeasureRepository measureRepository, DBFileRepository fileRepository) {
        this.measureService = measureService;
        this.validProtocolRepository = validProtocolRepository;
        this.modelMapper = modelMapper;
        this.machineRepository = machineRepository;
        this.modelRepository = modelRepository;
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
            throw new InvalidRequestException("Validation Type is not correct for this model");
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
        String html = "<html>\n<head>\n<style>\n.handwriting {\n  font-family: 'handwriting', serif;\n" +
                "}\n.arabic {\n  font-family: 'arabic', serif;\n}\n" +
                ".deja {\n  /* Contains glyphs for many languages. */\n  font-family: 'deja-sans', sans-serif;\n" +
                "}\n </style>\n</head>\n<body>\n\n" +
                "<div class=\"handwriting\" style=\"text-align:center;font-size: 90px;color:orange;\">\n" +
                "Hello World!\n</div>\n</body>\n</html>";


        try {
            Document document = new Document();
            OutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            InputStream inputStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream);
            document.close();
            DBFile dbFile = new DBFile();
            dbFile.setFileName("content");
            dbFile.setFileType("application/pdf");
            dbFile.setData(((ByteArrayOutputStream) outputStream).toByteArray());
            protocol.setProtocol(dbFile);
        } catch (Exception e) {
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
