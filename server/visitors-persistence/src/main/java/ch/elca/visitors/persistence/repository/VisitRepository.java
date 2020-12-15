package ch.elca.visitors.persistence.repository;

import ch.elca.visitors.persistence.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long>, QuerydslPredicateExecutor<Visit> {
}