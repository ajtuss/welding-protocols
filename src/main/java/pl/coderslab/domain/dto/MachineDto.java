package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class MachineDto {

    private Long id;
    private String serialNumber;
    private Long welderModelId;
    private String welderModelName;
    private Long welderModelBrandId;
    private String welderModelBrandName;
    private Long customerId;
    private String customerShortName;
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

    public Long getWelderModelId() {
        return welderModelId;
    }

    public void setWelderModelId(Long welderModelId) {
        this.welderModelId = welderModelId;
    }

    public String getWelderModelName() {
        return welderModelName;
    }

    public void setWelderModelName(String welderModelName) {
        this.welderModelName = welderModelName;
    }

    public Long getWelderModelBrandId() {
        return welderModelBrandId;
    }

    public void setWelderModelBrandId(Long welderModelBrandId) {
        this.welderModelBrandId = welderModelBrandId;
    }

    public String getWelderModelBrandName() {
        return welderModelBrandName;
    }

    public void setWelderModelBrandName(String welderModelBrandName) {
        this.welderModelBrandName = welderModelBrandName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerShortName() {
        return customerShortName;
    }

    public void setCustomerShortName(String customerShortName) {
        this.customerShortName = customerShortName;
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
                Objects.equals(welderModelId, that.welderModelId) &&
                Objects.equals(welderModelName, that.welderModelName) &&
                Objects.equals(welderModelBrandId, that.welderModelBrandId) &&
                Objects.equals(welderModelBrandName, that.welderModelBrandName) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(customerShortName, that.customerShortName) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, serialNumber, welderModelId, welderModelName, welderModelBrandId, welderModelBrandName, customerId, customerShortName, creationDate, modificationDate, versionId);
    }

    @Override
    public String toString() {
        return "MachineDto{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", welderModelId=" + welderModelId +
                ", welderModelName='" + welderModelName + '\'' +
                ", welderModelBrandId=" + welderModelBrandId +
                ", welderModelBrandName='" + welderModelBrandName + '\'' +
                ", customerId=" + customerId +
                ", customerShortName='" + customerShortName + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", versionId=" + versionId +
                '}';
    }
}
