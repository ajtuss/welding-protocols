package pl.coderslab.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BrandUpdateDTO {

    @NotNull
    private Long id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    @NotNull
    private Long versionId;
}
