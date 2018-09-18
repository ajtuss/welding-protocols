package pl.coderslab.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.core.Relation;
import pl.coderslab.domain.entities.PowerType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@Relation(value = "validation", collectionRelation = "validations")
public class ValidProtocolDTO {

    private Long id;

    @NotNull
    private Long machineId;

    private String protocolNumber;
    private String machineSerialNumber;
    private String machineInwNumber;
    private Long machineWelderModelId;
    private String machineWelderModelName;
    private Long machineWelderModelBrandId;
    private String machineWelderModelBrandName;
    private Long machineCustomerId;
    private String machineCustomerShortName;
    private LocalDateTime dateValidation;
    private LocalDateTime nextValidation;
    private Boolean result;
    private Boolean finalized;

    @NotNull
    private PowerType type;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean auto;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

}
