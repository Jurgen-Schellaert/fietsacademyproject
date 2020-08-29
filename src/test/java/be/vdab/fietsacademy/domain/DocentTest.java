package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class DocentTest{

    private final static BigDecimal WEDDE = BigDecimal.valueOf(200);
    private Docent docent1;
    private Campus campus1;

    @BeforeEach
    void beforeEach(){
        campus1 = new Campus("test", new Adres("test", "test", "test", "test"));
        docent1 = new Docent("test", "test", Geslacht.MAN, WEDDE, "test@test.be", campus1);
    }

    @Test
    void opslag(){
        docent1.opslag(BigDecimal.TEN);
        assertThat(docent1.getWedde()).isEqualByComparingTo("220");
    }

    @Test
    void opslagMet0Mislukt(){
        assertThatIllegalArgumentException().isThrownBy(() -> docent1.opslag(BigDecimal.ZERO));
    }

    @Test
    void opslagMetNullMislukt(){
        assertThatNullPointerException().isThrownBy(() -> docent1.opslag(null));
    }

    @Test
    void opslagMetNegatiefPercentageMislukt(){
        assertThatIllegalArgumentException().isThrownBy(() -> docent1.opslag(BigDecimal.valueOf(-1)));
    }

    @Test
    void eenNieuweDocentHeeftGeenBijnaam(){
        assertThat(docent1.getBijnamen().isEmpty());
    }

    @Test
    void bijnaamToevoegen(){
        assertThat(docent1.addBijnaam("test")).isTrue();
        assertThat(docent1.getBijnamen()).containsOnly("test");
    }

    @Test
    void eenBestaandeBijnaamToevoegenMislukt(){
        docent1.addBijnaam("test");
        assertThat(docent1.addBijnaam("test")).isFalse();
        assertThat(docent1.getBijnamen()).containsOnly("test");
    }

    @Test
    void nullAlsBijnaamMislukt(){
        assertThatNullPointerException().isThrownBy(() -> docent1.addBijnaam(null));
    }

    @Test
    void eenLegeBijnaamMislukt(){
        assertThatIllegalArgumentException().isThrownBy(() -> docent1.addBijnaam(""));
    }

    @Test
    void eenBijnaamMetEnkelSpatiesMislukt(){
        assertThatIllegalArgumentException().isThrownBy(() -> docent1.addBijnaam(" "));
    }

    @Test
    void bijnaamVerwijderen (){
        docent1.addBijnaam("test");
        assertThat(docent1.removeBijnaam("test")).isTrue();
        assertThat(docent1.getBijnamen()).isEmpty();
    }

    @Test
    void eenOnbestaandeBijnaamVerwijderenMislukt(){
        docent1.addBijnaam("test");
        assertThat(docent1.removeBijnaam("test2")).isFalse();
        assertThat(docent1.getBijnamen()).containsOnly("test");
    }
}
