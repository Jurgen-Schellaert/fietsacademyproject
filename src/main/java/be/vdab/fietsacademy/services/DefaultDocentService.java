package be.vdab.fietsacademy.services;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.exceptions.DocentNietGevondenException;
import be.vdab.fietsacademy.repositories.DocentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
class DefaultDocentService implements DocentService {

    private final DocentRepository repository;

    DefaultDocentService(DocentRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void opslag(long id, BigDecimal percentage) {
        Optional<Docent> optionalDocent = repository.findById(id);
        optionalDocent.ifPresentOrElse(docent -> docent.opslag(percentage), () -> {throw new DocentNietGevondenException();});
        /*if (optionalDocent.isPresent())
            optionalDocent.get().opslag(percentage);
        else
            throw new DocentNietGevondenException();*/
    }
}
