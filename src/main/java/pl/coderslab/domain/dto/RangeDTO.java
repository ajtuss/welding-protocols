package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.domain.constraints.RangeMatch;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RangeMatch.List({
        @RangeMatch(min = "IMin", max = "IMax", message = "iMax must be higher then iMin"),
        @RangeMatch(min = "UMin", max = "UMax", message = "uMax must be higher then uMin")
})
public class RangeDTO {

    @NotNull
    @DecimalMax("1000")
    private BigDecimal iMin;

    @NotNull
    @DecimalMax("1000")
    private BigDecimal iMax;

    @NotNull
    @DecimalMax("100.0")
    private BigDecimal uMin;

    @NotNull
    @DecimalMax("100.0")
    private BigDecimal uMax;

}
