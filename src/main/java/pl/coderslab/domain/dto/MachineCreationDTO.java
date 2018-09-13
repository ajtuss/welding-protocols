package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineCreationDTO {

    private String serialNumber;
    private String inwNumber;
    private Long welderModelId;
    private Long customerId;

}
