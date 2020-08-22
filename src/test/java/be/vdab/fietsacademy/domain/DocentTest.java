package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class DocentTest {

    private final static BigDecimal WEDDE = BigDecimal.valueOf(200);
    private Docent docent1;

    @BeforeEach
    void beforeEAch(){
        docent1 = new Docent("test", "test", Geslacht.MAN, WEDDE, "test@test.be");
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
}
