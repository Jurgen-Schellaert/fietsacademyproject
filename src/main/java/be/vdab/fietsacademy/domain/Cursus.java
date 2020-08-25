package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance (strategy =  InheritanceType.TABLE_PER_CLASS)
public abstract class Cursus {
    @Id
    private String id;
    private String naam;

    protected Cursus() {
    }

    public Cursus(String naam) {
        this.naam = naam;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
