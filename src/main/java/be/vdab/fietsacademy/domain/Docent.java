package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "docenten")
public class Docent {

    @Id
    private long id;
    private String voornaam;
    private String familienaam;
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;
    private BigDecimal wedde;
    private String emailAdres;

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
