package ch.elca.visitors.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ElcaVisitorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElcaVisitorsApplication.class);
    }

}