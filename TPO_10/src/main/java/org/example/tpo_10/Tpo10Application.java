package org.example.tpo_10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Tpo10Application {

    public static void main(String[] args) {
        //SpringApplication.run(Tpo10Application.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(Tpo10Application.class, args);
    }

}
