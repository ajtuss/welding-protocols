package pl.coderslab.domain.dto;

import lombok.Data;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@Relation(value = "customer", collectionRelation = "customers")
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
