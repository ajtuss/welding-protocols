package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class MachineDto {

    private Long id;
    private String serialNumber;
    private WelderModelDto welderModel;
    private CustomerDto customer;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public WelderModelDto getWelderModel() {
        return welderModel;
    }

    public void setWelderModel(WelderModelDto welderModel) {
        this.welderModel = welderModel;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
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
        MachineDto that = (MachineDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(serialNumber, that.serialNumber) &&
                Objects.equals(welderModel, that.welderModel) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, serialNumber, welderModel, customer, creationDate, modificationDate, versionId);
    }

    @Override
    public String toString() {
        return "MachineDto{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", welderModel=" + welderModel +
                ", customer=" + customer +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", versionId=" + versionId +
                '}';
    }
}
