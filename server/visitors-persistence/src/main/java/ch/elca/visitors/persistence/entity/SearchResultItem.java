package ch.elca.visitors.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class SearchResultItem {
    private String firstName;
    private String lastName;
    private Boolean status;
    private LocalDateTime dateTime;
}
