package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class TelefoonNrTest {
    private TelefoonNr nummer1, nogeensnummer1, nummer2;

    @BeforeEach
    void beforeEach(){
        nummer1 = new TelefoonNr("1", false, "");
        nogeensnummer1 = new TelefoonNr("1", false, "");
        nummer2 = new TelefoonNr("2", false, "");
    }

    @Test
    void telefoonNrsZijnGelijkAlsHunNummersGelijkZijn(){
        assertThat(nummer1).isEqualTo(nogeensnummer1);
    }

    @Test
    void telefoonNummersVerschillenAlsHunNummersVerschillen(){
        assertThat(nummer1).isNotEqualTo(nummer2);
    }

    @Test
    void eenTelefoonNummerVerschiltVanNull(){
        assertThat(nummer1).isNotEqualTo(null);
    }

    @Test
    void eenTelefoonNrVerschiltVanAndereObjectTypes(){
        assertThat(nummer1).isNotEqualTo((""));
    }

    @Test
    void gelijkeTelefoonNummersGevenDezelfdeHashCode(){
        assertThat(nummer1).hasSameHashCodeAs(nogeensnummer1);
    }
}
