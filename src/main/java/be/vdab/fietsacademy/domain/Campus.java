package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "campussen")
public class Campus {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @Embedded
    private Adres adres;
    @ElementCollection
    @CollectionTable(name="campussentelefoonnrs", joinColumns = @JoinColumn(name = "campusid"))
    @OrderBy("fax")
    private Set<TelefoonNr> telefoonNrs;

    protected Campus(){}

    public Campus(String naam, Adres adres) {
        this.naam = naam;
        this.adres = adres;
        telefoonNrs = new LinkedHashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Adres getAdres() {
        return adres;
    }



    public Set<TelefoonNr> getTelefoonNrs(){
        return Set.copyOf(telefoonNrs);
    }

    public boolean addTelefoonNr(TelefoonNr telefoonNr){
        if (telefoonNr == null)
            throw new NullPointerException();
        return telefoonNrs.add(telefoonNr);
    }

    public boolean removeTelefoonNr(TelefoonNr telefoonNr){
        return telefoonNrs.remove(telefoonNr);
    }
}
