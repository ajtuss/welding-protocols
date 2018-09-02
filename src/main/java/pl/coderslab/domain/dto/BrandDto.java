package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class BrandDto {

    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        BrandDto brandDto = (BrandDto) o;
        return Objects.equals(id, brandDto.id) &&
                Objects.equals(name, brandDto.name) &&
                Objects.equals(creationDate, brandDto.creationDate) &&
                Objects.equals(modificationDate, brandDto.modificationDate) &&
                Objects.equals(versionId, brandDto.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, creationDate, modificationDate, versionId);
    }

    @Override
    public String toString() {
        return "BrandDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", versionId=" + versionId +
                '}';
    }
}
