package rs.ac.uns.ftn.isa.pharmacy.domain.supply;

import rs.ac.uns.ftn.isa.pharmacy.domain.pharma.Drug;

import javax.persistence.*;

// TODO Composite key for object ids
/**
 * Amount of a specific drug which a given Supplier has in stock at the moment.
 */
@Entity
@Table(name = "supplier_drug_stock")
public class DrugStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Supplier supplier;
    @OneToOne
    private Drug drug;
    private int amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}