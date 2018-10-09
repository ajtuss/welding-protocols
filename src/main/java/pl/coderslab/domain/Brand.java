package pl.coderslab.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brands")
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends AbstractEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand")
    List<WelderModel> welderModels = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WelderModel> getWelderModels() {
        return welderModels;
    }

    public void setWelderModels(List<WelderModel> welderModels) {
        this.welderModels = welderModels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Brand brand = (Brand) o;
        return Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
