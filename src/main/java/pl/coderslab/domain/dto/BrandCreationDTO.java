package pl.coderslab.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class BrandCreationDTO {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
}
