package ch.elca.visitors.persistence.repository;

import ch.elca.visitors.persistence.entity.VisitorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorTypeRepository extends JpaRepository<VisitorType, Long> {
}
