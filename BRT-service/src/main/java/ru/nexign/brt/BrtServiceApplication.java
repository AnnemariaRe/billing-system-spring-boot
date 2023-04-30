package ru.nexign.brt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.nexign"})
@ComponentScan(basePackages = { "ru.nexign.jpa", "ru.nexign.brt"})
@EntityScan("ru.nexign.jpa.entity")
@EnableJpaRepositories({"ru.nexign.jpa", "ru.nexign.brt"})
@EnableCaching
public class BrtServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrtServiceApplication.class, args);
    }
}
