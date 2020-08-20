package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocentRepository {

    Optional<Docent> findById(long id);
}
