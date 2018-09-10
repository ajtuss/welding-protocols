package pl.coderslab.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WelderModelDTO {

    private Long id;
    private String name;
    private Long brandId;
    private String brandName;
    private Boolean mig;
    private Boolean mma;
    private Boolean tig;
    private Boolean plazma;
    private Boolean currentMeter;
    private Boolean voltageMeter;
    private Boolean stepControl;
    private Double migImin;
    private Double migImax;
    private Double migUmin;
    private Double migUmax;
    private Double mmaImin;
    private Double mmaImax;
    private Double mmaUmin;
    private Double mmaUmax;
    private Double tigImin;
    private Double tigImax;
    private Double tigUmin;
    private Double tigUmax;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

}