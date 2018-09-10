package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandCreationDTO {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
}
