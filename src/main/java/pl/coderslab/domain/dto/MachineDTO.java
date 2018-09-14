package pl.coderslab.domain.dto;

import lombok.Data;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@Relation(value = "machine", collectionRelation = "machines")
public class MachineDTO {

    private Long id;
    private String serialNumber;
    private String inwNumber;
    private Long welderModelId;
    private String welderModelName;
    private Long welderModelBrandId;
    private String welderModelBrandName;
    private Long customerId;
    private String customerShortName;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

    public MachineDTO() {
    }

    public MachineDTO(String serialNumber, String inwNumber, Long welderModelId, Long customerId) {
        this.serialNumber = serialNumber;
        this.inwNumber = inwNumber;
        this.welderModelId = welderModelId;
        this.customerId = customerId;
    }

    public MachineDTO(Long id, String serialNumber, String inwNumber, Long welderModelId, Long customerId, Long versionId) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.inwNumber = inwNumber;
        this.welderModelId = welderModelId;
        this.customerId = customerId;
        this.versionId = versionId;
    }

    public MachineDTO(Long id, String serialNumber, String inwNumber, Long welderModelId, String welderModelName, Long welderModelBrandId, String welderModelBrandName, Long customerId, String customerShortName, LocalDateTime creationDate, LocalDateTime modificationDate, Long versionId) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.inwNumber = inwNumber;
        this.welderModelId = welderModelId;
        this.welderModelName = welderModelName;
        this.welderModelBrandId = welderModelBrandId;
        this.welderModelBrandName = welderModelBrandName;
        this.customerId = customerId;
        this.customerShortName = customerShortName;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.versionId = versionId;
    }
}
