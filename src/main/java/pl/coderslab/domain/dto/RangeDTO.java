package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeDTO {

    private Long id;

    @Column(precision = 4, scale = 1)
    private Double iMin;

    @Column(precision = 4, scale = 1)
    private Double iMax;

    @Column(precision = 5, scale = 2)
    private Double uMin;

    @Column(precision = 5, scale = 2)
    private Double uMax;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

}
