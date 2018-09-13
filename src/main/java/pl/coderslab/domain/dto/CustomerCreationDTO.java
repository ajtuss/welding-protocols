package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreationDTO {

    private String shortName;
    private String fullName;
    private String city;
    private String zip;
    private String street;
    private String email;
    private String nip;

}
