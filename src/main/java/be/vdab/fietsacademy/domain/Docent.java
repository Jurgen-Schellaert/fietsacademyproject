package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "docenten")
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
