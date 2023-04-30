package ru.nexign.cdr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.nexign"})
@ComponentScan(basePackages = { "ru.nexign.jpa", "ru.nexign.cdr"})
@EntityScan("ru.nexign.jpa.entity")
@EnableJpaRepositories({"ru.nexign.jpa"})
public class CdrServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CdrServiceApplication.class, args);
    }

}
