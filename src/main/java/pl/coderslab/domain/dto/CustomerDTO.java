package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String shortName;
    private String fullName;
    private String city;
    private String zip;
    private String street;
    private String email;
    private String nip;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;


}
