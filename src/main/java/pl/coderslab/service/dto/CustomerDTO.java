package pl.coderslab.service.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.pl.NIP;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@Relation(value = "customer", collectionRelation = "customers")
public class CustomerDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 15)
    private String shortName;

    @NotNull
    @Size(min = 3)
    private String fullName;

    @NotNull
    private String city;

    @NotNull
    private String zip;

    @NotNull
    private String street;

    @Email
    private String email;

    @NIP
    private String nip;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

    public CustomerDTO() {
    }

    public CustomerDTO(String shortName, String fullName, String city, String zip, String street, String email, String nip) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.city = city;
        this.zip = zip;
        this.street = street;
        this.email = email;
        this.nip = nip;
    }

    public CustomerDTO(Long id, String shortName, String fullName, String city, String zip, String street, String email, String nip, Long versionId) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.city = city;
        this.zip = zip;
        this.street = street;
        this.email = email;
        this.nip = nip;
        this.versionId = versionId;
    }

    public CustomerDTO(Long id, String shortName, String fullName, String city, String zip, String street, String email, String nip, LocalDateTime creationDate, LocalDateTime modificationDate, Long versionId) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.city = city;
        this.zip = zip;
        this.street = street;
        this.email = email;
        this.nip = nip;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.versionId = versionId;
    }
}
