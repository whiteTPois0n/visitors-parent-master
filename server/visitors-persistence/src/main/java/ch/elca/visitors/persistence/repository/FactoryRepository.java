package ch.elca.visitors.persistence.repository;

import ch.elca.visitors.persistence.entity.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {

}
