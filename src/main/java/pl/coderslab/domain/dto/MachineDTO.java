package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
