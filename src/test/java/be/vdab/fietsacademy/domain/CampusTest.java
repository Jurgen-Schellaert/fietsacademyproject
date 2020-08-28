package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CampusTest {

    private Campus campus = new Campus("Noord", new Adres("test", "test", "test", "test"));
    private TelefoonNr nummer1, nogeensnummer1, nummer2;

    @BeforeEach
    void beforeEach(){
        nummer1 = new TelefoonNr("1", false, "");
        nogeensnummer1 = new TelefoonNr("1", false, "");
        nummer2 = new TelefoonNr("2", false, "");
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
}
