package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WelderModelUpdateDTO {

    private Long id;
    private String name;
    private Long brandId;
    private Boolean mig;
    private Boolean mma;
    private Boolean tig;
    private Boolean currentMeter;
    private Boolean voltageMeter;
    private Boolean stepControl;
    private RangeDTO migRange;
    private RangeDTO mmaRange;
    private RangeDTO tigRange;
    private Long versionId;

}