package rs.ac.uns.ftn.isa.pharmacy.domain.users.admin;

import rs.ac.uns.ftn.isa.pharmacy.domain.users.user.Person;

import javax.persistence.*;

@Table(name="admins")
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Person person;
    private AdminType adminType;

    public AdminType getAdminType() {
        return adminType;
    }

    public void setAdminType(AdminType adminType) {
        this.adminType = adminType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
