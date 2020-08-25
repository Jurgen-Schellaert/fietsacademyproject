package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.GroepsCursus;
import be.vdab.fietsacademy.domain.IndividueleCursus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@DataJpaTest
@Import(JpaCursusRepository.class)
@Sql("/insertCursus.sql")
class JpaCursusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaCursusRepository repository;
    private static final String GROEPSCURSUSSEN = "groepscursussen";
    private static final String INDIVIDUELE_CURSUSSEN = "individuelecursussen";
    private static final LocalDate EEN_DATUM=LocalDate.of(2019,1,1);
    private final EntityManager manager;

    public JpaCursusRepositoryTest(JpaCursusRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private String idVanTestGroepsCursus(){
        return super.jdbcTemplate.queryForObject("select id from groepscursussen where naam = 'testGroep'", String.class);
    }

    private String idVanTestIndividueleCursus(){
        return super.jdbcTemplate.queryForObject("select id from individuelecursussen where naam = 'testIndividueel'", String.class);
    }

    @Test
    void findGroepsCursusById(){
        assertThat(repository.findById(idVanTestGroepsCursus()).get().getNaam()).isEqualTo("testGroep");
    }

    @Test
    void findIndividueleCursusById(){
        assertThat(repository.findById(idVanTestIndividueleCursus()).get().getNaam()).isEqualTo("testIndividueel");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(repository.findById("")).isNotPresent();
    }

    @Test
    void createGroupsCursus(){
        GroepsCursus gCursus = new GroepsCursus("testGroep2", EEN_DATUM, EEN_DATUM);
        repository.create(gCursus);
        manager.flush();
        assertThat(super.countRowsInTableWhere(GROEPSCURSUSSEN, "id = '" + gCursus.getId() + "'")).isOne();
    }

    @Test
    void createIndividueleCursus(){
        IndividueleCursus iCursus = new IndividueleCursus("testIndividueel2", 7);
        repository.create(iCursus);
        manager.flush();
        assertThat(super.countRowsInTableWhere(INDIVIDUELE_CURSUSSEN, "id = '" + iCursus.getId() +"'")).isOne();
    }
}