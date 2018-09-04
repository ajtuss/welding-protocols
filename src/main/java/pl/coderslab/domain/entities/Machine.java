package pl.coderslab.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "machines")
public class Machine extends AbstractEntity{

    @NotNull
    private String serialNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private WelderModel welderModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "machine",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<ValidProtocol> validProtocols= new ArrayList<>();

    public void addValidProtocol(ValidProtocol validProtocol){
        validProtocols.add(validProtocol);
        validProtocol.setMachine(this);
    }

    public void removeValidProtocol(ValidProtocol validProtocol){
        validProtocols.remove(validProtocol);
        validProtocol.setMachine(null);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public WelderModel getWelderModel() {
        return welderModel;
    }

    public void setWelderModel(WelderModel welderModel) {
        this.welderModel = welderModel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ValidProtocol> getValidProtocols() {
        return validProtocols;
    }

    public void setValidProtocols(List<ValidProtocol> validProtocols) {
        this.validProtocols = validProtocols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Machine machine = (Machine) o;
        return Objects.equals(serialNumber, machine.serialNumber) &&
                Objects.equals(welderModel, machine.welderModel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), serialNumber, welderModel);
    }

    @Override
    public String toString() {
        return "Machine{" +
                "serialNumber='" + serialNumber + '\'' +
                ", welderModel=" + welderModel +
                "} " + super.toString();
    }
}
