package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WelderModelUpdateDTO {

    private Long id;
    private String name;
    private Long brandId;
    private String brandName;
    private Boolean mig;
    private Boolean mma;
    private Boolean tig;
    private Boolean currentMeter;
    private Boolean voltageMeter;
    private Boolean stepControl;
    private RangeUpdateDTO migRange;
    private RangeUpdateDTO mmaRange;
    private RangeUpdateDTO tigRange;
    private Long versionId;

}