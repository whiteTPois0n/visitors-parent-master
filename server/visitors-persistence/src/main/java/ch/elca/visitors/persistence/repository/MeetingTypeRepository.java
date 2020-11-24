package ch.elca.visitors.persistence.repository;

import ch.elca.visitors.persistence.entity.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType, Long> {

}
