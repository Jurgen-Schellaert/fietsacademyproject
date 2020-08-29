package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
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
    @OneToMany
    @JoinColumn(name = "campusid")
    @OrderBy("voornaam, familienaam")
    private Set<Docent> docenten;

    protected Campus(){}

    public Campus(String naam, Adres adres) {
        this.naam = naam;
        this.adres = adres;
        this.telefoonNrs = new LinkedHashSet<>();
        this.docenten = new LinkedHashSet<>();
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

    public Set<Docent> getDocenten(){
        return Collections.unmodifiableSet(docenten);
    }

    public boolean add(Docent docent){
        if (docent == null)
            throw new NullPointerException();
        return docenten.add(docent);
    }

    public boolean remove(Docent docent) {
        return docenten.remove(docent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campus)) return false;
        Campus campus = (Campus) o;
        return Objects.equals(naam.toUpperCase(), campus.naam.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam.toUpperCase());
    }
}
