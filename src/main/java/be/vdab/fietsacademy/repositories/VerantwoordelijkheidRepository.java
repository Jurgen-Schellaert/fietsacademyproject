package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Verantwoordelijkheid;

import java.util.Optional;

public interface VerantwoordelijkheidRepository {

    Optional<Verantwoordelijkheid> findById(long id);
    void create(Verantwoordelijkheid verantwoordelijkheid);
}
