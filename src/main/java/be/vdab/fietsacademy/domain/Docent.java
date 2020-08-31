package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "docenten")
/*@NamedQuery(name = "Docent.findByWeddeBetween", query = "select d from Docent d where d.wedde between :van and :tot " +
                                                       "order by d.wedde, d.id")*/
public class Docent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String voornaam;
    private String familienaam;
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;
    private BigDecimal wedde;
    private String emailAdres;
    @ElementCollection
    @CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name="docentid"))
    @Column(name ="bijnaam")
    private Set<String> bijnamen;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campusid")
    private Campus campus;
    @ManyToMany(mappedBy = "docenten")
    private Set<Verantwoordelijkheid> verantwoordelijkheden;

    protected Docent() {
    }

    public Docent(String voornaam, String familienaam, Geslacht geslacht, BigDecimal wedde, String emailAdres, Campus campus) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.geslacht = geslacht;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.bijnamen = new LinkedHashSet<>();
        setCampus(campus);
        verantwoordelijkheden = new LinkedHashSet<>();
    }

    public void opslag(BigDecimal percentage){
        if (percentage.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException();
        BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        wedde = wedde.multiply(factor, new MathContext(2, RoundingMode.HALF_UP));
    }

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }

    public BigDecimal getWedde() {
        return wedde;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public Set<String> getBijnamen() {
        return Set.copyOf(bijnamen);
    }

    public boolean addBijnaam(String bijnaam) {
        // if bijnaam happens to be null, the following test will throw a NullPointerException as isEmpty()/isBlank()
        // cannot inspect null
        if (bijnaam.isBlank() || bijnaam.isEmpty())
            throw new IllegalArgumentException();
        return bijnamen.add(bijnaam);
    }

    public boolean removeBijnaam(String bijnaam){
        return bijnamen.remove(bijnaam);
    }

    public Campus getCampus() {
        return campus;
    }

    public final void setCampus(Campus campus) {
        if (!campus.getDocenten().contains(this))
            campus.add(this);
        this.campus = campus;
    }

    public boolean add(Verantwoordelijkheid verantwoordelijkheid){
        boolean added = verantwoordelijkheden.add(verantwoordelijkheid);
        if (!verantwoordelijkheid.getDocenten().contains(this))
            verantwoordelijkheid.add(this);
        return added;
    }


    public boolean remove(Verantwoordelijkheid verantwoordelijkheid){
        boolean removed = verantwoordelijkheden.remove(verantwoordelijkheid);
        if (verantwoordelijkheid.getDocenten().contains(this))
            verantwoordelijkheid.remove(this);
        return removed;
    }

    public Set<Verantwoordelijkheid> getVerantwoordelijkheden(){
        return Collections.unmodifiableSet(verantwoordelijkheden);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Docent) {
            Docent andereDocent = (Docent) o;
            return emailAdres.equalsIgnoreCase(andereDocent.emailAdres);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return emailAdres == null? 0 : emailAdres.toLowerCase().hashCode();
    }

}
