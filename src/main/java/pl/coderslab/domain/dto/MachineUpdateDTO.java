package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineUpdateDTO {

    private Long id;
    private String serialNumber;
    private String inwNumber;
    private Long welderModelId;
    private Long customerId;
    private Long versionId;

}
