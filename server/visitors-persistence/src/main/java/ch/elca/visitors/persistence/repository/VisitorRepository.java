package ch.elca.visitors.persistence.repository;

import ch.elca.visitors.persistence.entity.SearchResultItem;
import ch.elca.visitors.persistence.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>, QuerydslPredicateExecutor<Visitor> {

    @Query(nativeQuery = true)
    List<SearchResultItem> search(String firstName, String lastName, int limit, Long offset);
}
