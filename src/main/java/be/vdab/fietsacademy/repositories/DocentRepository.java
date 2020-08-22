package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;

import java.util.Optional;

// do not use @Repository annotation in the interface !!!!! Only in the implementing classes!!
public interface DocentRepository {

    Optional<Docent> findById(long id);
    void create(Docent docent);
    void delete(long id);
}
