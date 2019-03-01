package io.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 16.02.2019
 */
@EnableJpaRepositories
@ComponentScan
@SpringBootApplication
public class AppStarter {
    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }
}
