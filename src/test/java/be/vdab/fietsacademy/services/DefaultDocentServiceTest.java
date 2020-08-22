package be.vdab.fietsacademy.services;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.domain.Geslacht;
import be.vdab.fietsacademy.exceptions.DocentNietGevondenException;
import be.vdab.fietsacademy.repositories.DocentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultDocentServiceTest {

    private DefaultDocentService service;
    @Mock
    private DocentRepository repository;
    private Docent docent;

    @BeforeEach
    void beforeEach(){
        docent = new Docent("test",
                "test",
                Geslacht.MAN,
                BigDecimal.valueOf(100),
                "test@test.be");
        service = new DefaultDocentService(repository);
    }

    @Test
    void opslag(){
        when(repository.findById(1)).thenReturn(Optional.of(docent));   //findById() will be called by Docent method getWedde()
                                                                        // when it is, make it return the docent instance specified above
        service.opslag(1, BigDecimal.TEN);
        assertThat(docent.getWedde()).isEqualByComparingTo("110");      // here is where findById() will be called indirectly
        verify(repository).findById(1);                                 // verify that findById() has effectively been called
    }

    @Test
    void opslagVoorOnbestaandeDocent(){
        // inexistent id of -1
        assertThatExceptionOfType(DocentNietGevondenException.class).isThrownBy(() -> service.opslag(-1, BigDecimal.TEN));
        verify(repository).findById(-1);
    }
}
