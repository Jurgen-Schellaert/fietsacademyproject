package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaVerantwoordelijkheidRepository.class)
@Sql("/insertCampus.sql")
@Sql("/insertDocent.sql")
@Sql("/insertVerantwoordelijkheid.sql")
@Sql("/insertDocentVerantwoordelijkheid.sql")
public class JpaVerantwoordelijkheidRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaVerantwoordelijkheidRepository repository;
    private Verantwoordelijkheid verantwoordelijkheid;
    private Campus campus;
    private Docent docent;
    private final static String VERANTWOORDELIJKHEDEN = "verantwoordelijkheden";
    private final EntityManager manager;

    JpaVerantwoordelijkheidRepositoryTest(JpaVerantwoordelijkheidRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @BeforeEach
    void beforeEach(){
        campus = new Campus("tcampus", new Adres("test", "test", "test", "test"));
        docent = new Docent("testD", "testD", Geslacht.MAN, BigDecimal.valueOf(1000), "testD@test.be", campus);
        verantwoordelijkheid = new Verantwoordelijkheid("brandweer");
    }

    private long idVanTestVerantwoordelijkheid(){
        return super.jdbcTemplate.queryForObject("select id from verantwoordelijkheden where naam = 'test'", Long.class);
    }

    @Test
    @DisplayName("findById haalt de juiste verantwoordelijkheid op")
    void findByIdHaaltJuisteVerantwoordelijkheidOp(){
        assertThat(repository.findById(idVanTestVerantwoordelijkheid()).get().getNaam()).isEqualTo("test");
    }

    @Test
    @DisplayName("Een onbestaand Id haalt niets op")
    void onbestaandIdHaaltNietsOp(){
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    @DisplayName("nieuwe verantwoordelijkheden worden correct aangemaakt")
    void create(){
        repository.create(verantwoordelijkheid);
        // manager.flush() not required here as flushing is automatic when a record is inserted that gets an AUTOMATIC KEY from the DB
        assertThat(super.countRowsInTableWhere(VERANTWOORDELIJKHEDEN, "naam = 'brandweer'")).isOne();
    }

    @Test
    @DisplayName("een verantwoordelijkheid draagt de bijhorende lijst docenten met zich mee")
    void docentenLezen(){
        Verantwoordelijkheid verantwoordelijkheid = repository.findById(idVanTestVerantwoordelijkheid()).get();
        assertThat(verantwoordelijkheid.getDocenten())
                .allSatisfy(docent -> docent.getVerantwoordelijkheden().contains(verantwoordelijkheid));
    }

    @Test
    @DisplayName("nieuwe verantwoordelijkheden worden correct aan de database toegevoegd")
    void eenDocentToevoegen(){
        manager.persist(campus);
        manager.persist(docent);
        repository.create(verantwoordelijkheid);
        verantwoordelijkheid.add(docent);
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject("select docentid from docentenverantwoordelijkheden " +
                                                    "where verantwoordelijkheidid = ?", Long.class, verantwoordelijkheid.getId()))
                        .isEqualTo(docent.getId());
    }


}
