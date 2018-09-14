package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeDTO {

    @NotNull
    @Column(precision = 4, scale = 1)
    private Double iMin;

    @NotNull
    @Column(precision = 4, scale = 1)
    private Double iMax;

    @NotNull
    @Column(precision = 5, scale = 2)
    private Double uMin;

    @NotNull
    @Column(precision = 5, scale = 2)
    private Double uMax;

}
