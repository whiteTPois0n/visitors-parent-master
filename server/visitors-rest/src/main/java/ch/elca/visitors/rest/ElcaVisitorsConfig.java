package ch.elca.visitors.rest;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "ch.elca.visitors.persistence")
@EnableJpaRepositories(basePackages = "ch.elca.visitors.persistence.repository")
@EnableJpaAuditing
@ComponentScan(basePackages = "ch.elca.visitors")
public class ElcaVisitorsConfig {

}
