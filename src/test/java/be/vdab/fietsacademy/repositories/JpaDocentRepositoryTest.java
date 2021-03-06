package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(JpaDocentRepository.class)
@Sql("/insertCampus.sql")
@Sql("/insertVerantwoordelijkheid.sql")
@Sql("/insertDocent.sql")
@Sql("/insertDocentVerantwoordelijkheid.sql")
class JpaDocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaDocentRepository repository;
    private final static String DOCENTEN = "docenten";
    private Docent docent;
    private final EntityManager manager;
    private Campus campus;

    JpaDocentRepositoryTest(JpaDocentRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @BeforeEach
    void beforeEach(){
        campus = new Campus("test", new Adres("test", "test", "test", "test"));
        docent = new Docent("test", "test", Geslacht.MAN, BigDecimal.TEN, "test@test.be", campus);
        //campus.add(docent);
    }

    private long idVanTestMan(){
        return super.jdbcTemplate.queryForObject("select id from docenten where voornaam = 'testM'", Long.class);
    }

    private long idVanTestVrouw(){
        return super.jdbcTemplate.queryForObject("select id from docenten where voornaam = 'testV'", Long.class);
    }

    @Test
    void findById(){
        assertThat(repository.findById(idVanTestMan()).get().getVoornaam()).isEqualTo("testM");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void man(){
        assertThat(repository.findById(idVanTestMan()).get().getGeslacht()).isEqualTo(Geslacht.MAN);
    }

    @Test
    void vrouw(){
        assertThat(repository.findById(idVanTestVrouw()).get().getGeslacht()).isEqualTo(Geslacht.VROUW);
    }

    @Test
    void create(){
       // the campus db insert (persist(campus) is mandatory - create(docent) will fail if the docent it holds exists
       // only in memory (=transient), not in the database. The Exception message would state this:
       //Caused by: org.hibernate.TransientPropertyValueException: Not-null property references a transient value - transient
       //instance must be saved before current operation : be.vdab.fietsacademy.domain.Docent.campus -> be.vdab.fietsacademy.domain.Campus
        manager.persist(campus);
        repository.create(docent);
        manager.flush();
        /*
        // docent, created without a value for its id member in the beforeEach method above, has been initialized
        // to the default int value 0
        // when it is written to the table, this id gets updated - as per the @Autogeneratad annotation - by the
        // value generated by the database - thus it no longer contains the same id value it originally held
        System.out.println("********************************************************");
        // this shows that, stored in the db, its id is not any longer 0 ...
        System.out.println(super.jdbcTemplate.queryForObject("select id from docenten where voornaam = 'test'", Long.class));
        // and this shows that the docent variable declared in the current file, which held an id of 0 until written out,
        // now holds a different id, viz. the one contained in its db record
        System.out.println(docent.getId());
        */
        assertThat(docent.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(DOCENTEN, "id=" + docent.getId())).isOne();
        assertThat(super.jdbcTemplate.queryForObject(
                "select campusid from docenten where id =?", Long.class, docent.getId()))
                    .isEqualTo(campus.getId());
    }

    @Test
    void remove(){
        long id = idVanTestVrouw();
        repository.delete(id);
        manager.flush();
        assertThat(super.countRowsInTableWhere(DOCENTEN, "id=" + id)).isZero();
    }

    @Test
    void findAll(){
        assertThat(repository.findAll()).hasSize(super.countRowsInTable(DOCENTEN))
                            .extracting(docent -> docent.getWedde())
                            .isSorted();
    }

    @Test
    void findByWeddeBetween(){
        BigDecimal duizend = BigDecimal.valueOf(1000);
        BigDecimal tweeduizend= BigDecimal.valueOf(2000);
        List<Docent> docenten = repository.findByWeddeBetween(duizend, tweeduizend);
        assertThat(docenten).hasSize(super.countRowsInTableWhere(DOCENTEN, "wedde between 1000 and 2000"));
    }

    @Test
    void findEmailAdressen(){
        assertThat(repository.findEmailAdressen()).hasSize(super.jdbcTemplate.queryForObject(
                                                              "select count(emailAdres) from docenten", Integer.class))
                                                    .allSatisfy(emailadres -> assertThat(emailadres).contains("@"));
    }

    @Test
    void findIdsEnEmailAdressen(){
        assertThat(repository.findIdsEnEmailAdressen()).hasSize(super.countRowsInTable(DOCENTEN));
    }

    @Test
    void findGrootsteWedde(){
        assertThat(repository.findGrootsteWedde())
                .isEqualByComparingTo(super.jdbcTemplate.queryForObject("select max(wedde) from docenten", BigDecimal.class));
    }

    @Test
    void findAantalDocentenPerWedde(){
        BigDecimal duizend = BigDecimal.valueOf(1000);
        assertThat(repository.findAantalDocentenPerWedde())
                                .hasSize(super.jdbcTemplate.queryForObject("select count(distinct wedde) from docenten", Integer.class))
                                .filteredOn(aantalPerWedde -> aantalPerWedde.getWedde().compareTo(duizend) == 0)
                                .allSatisfy(aantalPerWedde -> assertThat(aantalPerWedde.getAantal())
                                .isEqualTo(super.countRowsInTableWhere(DOCENTEN, "wedde = 1000")));
    }

    @Test
    void algemeneOpslag(){
        assertThat(repository.algemeneOpslag(BigDecimal.TEN)).isEqualTo(super.countRowsInTable(DOCENTEN));
        assertThat(super.jdbcTemplate.queryForObject("select wedde from docenten where id = ?", BigDecimal.class, idVanTestMan()))
                .isEqualByComparingTo("1100");
    }

    @Test
    void bijnamenLezen(){
        assertThat(repository.findById(idVanTestMan()).get().getBijnamen()).containsOnly("test");
    }

    @Test
    void bijnaamToevoegen(){
        manager.persist(campus);
        repository.create(docent);
        docent.addBijnaam("test");
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject(
                "select bijnaam from docentenbijnamen where docentid = ?", String.class, docent.getId()))
                    .isEqualTo("test");
    }

    @Test
    void campusLazyLoaded(){
        Docent docent = repository.findById(idVanTestMan()).get();
        assertThat(docent.getCampus().getNaam()).isEqualTo("test");
    }

    /*
    // shows that updating an entity (here its wedde column) will propagate to the DB even when
    // it its not inside a service class - but one will have to call manager.flush() to make
    // sure that the update is made before it is read by the assertion test
    @Test
    void slaOp(){
        Docent d = repository.findById(idVanTestMan()).get();
        d.opslag(BigDecimal.valueOf(20));
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject(
                "select wedde from docenten where id = ?", BigDecimal.class, idVanTestMan()))
                .isEqualByComparingTo("1200");
    }
    */

    @Test
    void verantwoordelijkhedenLezen(){
        assertThat(repository.findById(idVanTestMan()).get().getVerantwoordelijkheden())
                .containsOnly(new Verantwoordelijkheid(("test")));
    }

    @Test
    void verantwoordelijkheidToevoegen(){
        Verantwoordelijkheid verantwoordelijkheid = new Verantwoordelijkheid("test2");
        manager.persist(verantwoordelijkheid);
        manager.persist(campus);
        repository.create(docent);
        docent.add(verantwoordelijkheid);
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject("select verantwoordelijkheidid from docentenverantwoordelijkheden " +
                                                        "where docentid = ?", Long.class, docent.getId()))
                                        .isEqualTo(verantwoordelijkheid.getId());
    }


}