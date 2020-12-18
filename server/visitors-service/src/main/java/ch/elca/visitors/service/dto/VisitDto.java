package ch.elca.visitors.service.dto;

import ch.elca.visitors.persistence.enumeration.VisitorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {

    private Long id;

    private VisitorType visitorType;

    private String reasonOfVisit;

    private String badgeNumber;

    private Double temperature;

    private LocalDateTime checkedOut;

    private VisitorDto visitor;

    private AppointmentDto appointment;

}
