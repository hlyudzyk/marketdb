package app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"persistence.repositories","businesslogic.services", "businesslogic.validation.validationservices"})
public class ProjectConfig {

}