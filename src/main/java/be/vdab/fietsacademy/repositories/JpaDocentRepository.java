package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;

import javax.persistence.EntityManager;
import java.util.Optional;

public class JpaDocentRepository implements DocentRepository{

    private final EntityManager manager;
    JpaDocentRepository(EntityManager manager){
        this.manager = manager;
    }

    @Override
    public Optional<Docent> findById(long id){
        return Optional.ofNullable(manager.find(Docent.class, id));
    }
}
