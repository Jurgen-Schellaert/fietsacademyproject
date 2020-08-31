package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Verantwoordelijkheid;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JpaVerantwoordelijkheidRepository implements VerantwoordelijkheidRepository{

    private final EntityManager manager;

    JpaVerantwoordelijkheidRepository(EntityManager manager){
        this.manager = manager;
    }

    @Override
    public Optional<Verantwoordelijkheid> findById(long id) {
        return Optional.ofNullable(manager.find(Verantwoordelijkheid.class, id));
    }

    @Override
    public void create(Verantwoordelijkheid verantwoordelijkheid) {
        manager.persist(verantwoordelijkheid);
    }
}
