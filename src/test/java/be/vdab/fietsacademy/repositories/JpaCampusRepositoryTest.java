package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Adres;
import be.vdab.fietsacademy.domain.Campus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@DataJpaTest
@Import(JpaCampusRepository.class)
@Sql("/insertCampus.sql")
class JpaCampusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaCampusRepository repository;
    private final static String CAMPUSSEN = "campussen";

    public JpaCampusRepositoryTest(JpaCampusRepository repository) {
        this.repository = repository;
    }

    private long idOfTestCampus(){
        return super.jdbcTemplate.queryForObject(
                "select id from campussen where naam = 'test'", Long.class);
    }

    @Test
    void findById(){
        assertThat((repository.findById(idOfTestCampus())).get().getNaam()).isEqualTo("test");
    }

    @Test
    void invalidIdHasNoMatches(){
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void create(){
        Adres adres = new Adres("test", "test", "test", "test");
        Campus campus = new Campus("testcampus'", adres);
        repository.create(campus);
        assertThat(super.countRowsInTableWhere(CAMPUSSEN, "id = '" + campus.getId() + "'")).isOne();
    }

}
