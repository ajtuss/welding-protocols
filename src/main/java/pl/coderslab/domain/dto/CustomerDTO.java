package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shortName, that.shortName) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(city, that.city) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(street, that.street) &&
                Objects.equals(email, that.email) &&
                Objects.equals(nip, that.nip) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shortName, fullName, city, zip, street, email, nip, creationDate, modificationDate, versionId);
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", street='" + street + '\'' +
                ", email='" + email + '\'' +
                ", nip='" + nip + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", versionId=" + versionId +
                '}';
    }
}
