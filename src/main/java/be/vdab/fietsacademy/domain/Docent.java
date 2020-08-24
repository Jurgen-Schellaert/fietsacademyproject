package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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

    protected Docent() {
    }

    public Docent(String voornaam, String familienaam, Geslacht geslacht, BigDecimal wedde, String emailAdres) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.geslacht = geslacht;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
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
}
