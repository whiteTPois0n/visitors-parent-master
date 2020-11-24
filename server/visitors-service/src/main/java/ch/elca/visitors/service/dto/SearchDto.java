package ch.elca.visitors.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {

    private String firstName;
    private String lastName;
    private LocalDateTime dateTime;

}
