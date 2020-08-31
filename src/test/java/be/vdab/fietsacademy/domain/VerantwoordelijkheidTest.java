package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class VerantwoordelijkheidTest {

    private Verantwoordelijkheid verantwoordelijkheid1, verantwoordelijkheid2;
    private Campus campus;
    private Docent docent1, docent2;


    @BeforeEach
    void beforeEach(){
            verantwoordelijkheid1 = new Verantwoordelijkheid("EHBO");
            verantwoordelijkheid2 = new Verantwoordelijkheid("Brandweer");
            campus = new Campus("testcampus", new Adres("test", "test", "test", "test"));
            docent1 = new Docent("test", "test", Geslacht.MAN,
                                    BigDecimal.valueOf(1000), "test@test.be", campus);
    }

    @Test
    void docentToevoegen(){
        assertThat(verantwoordelijkheid1.getDocenten()).isEmpty();
        assertThat(verantwoordelijkheid1.add(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.getDocenten()).containsOnly(docent1);
        assertThat(docent1.getVerantwoordelijkheden()).containsOnly(verantwoordelijkheid1);
    }

    @Test
    void docentVerwijderen(){
        assertThat(verantwoordelijkheid1.getDocenten()).isEmpty();
        assertThat(verantwoordelijkheid1.add(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.remove(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.getDocenten()).isEmpty();
        assertThat(docent1.getVerantwoordelijkheden()).isEmpty();
    }

}
