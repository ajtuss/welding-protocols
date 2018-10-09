package pl.coderslab.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "models")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WelderModel extends AbstractEntity {

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "welderModel")
    private List<Machine> machines = new ArrayList<>();

    private Boolean mig;

    private Boolean mma;

    private Boolean tig;

    private Boolean currentMeter;

    private Boolean voltageMeter;

    private Boolean stepControl;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mig_range")
    private Range migRange;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mma_range")
    private Range mmaRange;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tig_range")
    private Range tigRange;


    public void addMachine(Machine machine) {
        machines.add(machine);
        machine.setWelderModel(this);
    }

    public void removeMachine(Machine machine) {
        machines.remove(machine);
        machine.setWelderModel(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WelderModel that = (WelderModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(mig, that.mig) &&
                Objects.equals(mma, that.mma) &&
                Objects.equals(tig, that.tig) &&
                Objects.equals(currentMeter, that.currentMeter) &&
                Objects.equals(voltageMeter, that.voltageMeter) &&
                Objects.equals(stepControl, that.stepControl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, brand, mig, mma, tig, currentMeter, voltageMeter, stepControl);
    }

    @Override
    public String toString() {
        return "WelderModel{" +
                "name='" + name + '\'' +
                ", brand=" + brand +
                ", mig=" + mig +
                ", mma=" + mma +
                ", tig=" + tig +
                ", currentMeter=" + currentMeter +
                ", voltageMeter=" + voltageMeter +
                ", stepControl=" + stepControl +
                ", migRange=" + migRange +
                ", mmaRange=" + mmaRange +
                ", tigRange=" + tigRange +
                "} " + super.toString();
    }
}
