package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class CampusTest {

    private Campus campus = new Campus("Noord", new Adres("test", "test", "test", "test"));
    private TelefoonNr nummer1, nogeensnummer1, nummer2;
    private Docent docent1;
    private Campus campus1;
    private Campus campus2;

    @BeforeEach
    void beforeEach(){
        nummer1 = new TelefoonNr("1", false, "");
        nogeensnummer1 = new TelefoonNr("1", false, "");
        nummer2 = new TelefoonNr("2", false, "");
        campus1 = new Campus("test", new Adres("test", "test", "test", "test"));
        campus2 = new Campus("test2", new Adres("test2","test2","test2","test2"));
        docent1 = new Docent(
                "test", "test", Geslacht.MAN, BigDecimal.TEN, "test@test.be", campus1);
    }

    @Test
    void eenNieuweCampusHeeftGeenTelefoonNummer(){
        assertThat(campus.getTelefoonNrs().isEmpty());
    }

    @Test
    void telefoonNummerToevoegen(){
        assertThat(campus.addTelefoonNr(nummer1)).isTrue();
        assertThat(campus.getTelefoonNrs()).containsOnly(nummer1);
    }

    @Test
    void eenBestaandeBijnaamToevoegenMislukt(){
        campus.addTelefoonNr(nummer1);
        assertThat(campus.addTelefoonNr(nogeensnummer1)).isFalse();
        assertThat(campus.getTelefoonNrs()).containsOnly(nummer1);
    }

    @Test
    void nullAlsBijnaamMislukt(){
        assertThatNullPointerException().isThrownBy(() -> campus.addTelefoonNr(null));
    }

    @Test
    void bijnaamVerwijderen (){
        campus.addTelefoonNr(nummer1);
        assertThat(campus.removeTelefoonNr(nummer1)).isTrue();
        assertThat(campus.getTelefoonNrs()).isEmpty();
    }

    @Test
    void eenOnbestaandeBijnaamVerwijderenMislukt(){
        campus.addTelefoonNr(nummer1);
        assertThat(campus.removeTelefoonNr(nummer2)).isFalse();
        assertThat(campus.getTelefoonNrs()).containsOnly(nummer1);
    }

    @Test
    void campus1IsDeCampusVanDocent1(){
        assertThat(docent1.getCampus()).isEqualTo(campus1);
        assertThat(campus1.getDocenten()).containsOnly(docent1);
    }

    @Test
    void docent1VerhuistNaarCampus2() {
        assertThat(campus2.add(docent1)).isTrue();
        assertThat(campus1.getDocenten()).isEmpty();
        assertThat(campus2.getDocenten()).containsOnly(docent1);
        assertThat(docent1.getCampus()).isEqualTo(campus2);
    }
    @Test
    void eenNullDocentToevoegenMislukt() {
        assertThatNullPointerException().isThrownBy(() -> campus1.add(null));
    }


}
