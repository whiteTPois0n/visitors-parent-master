package ch.elca.visitors.persistence.repository;

import ch.elca.visitors.persistence.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, QuerydslPredicateExecutor<Appointment> {
}