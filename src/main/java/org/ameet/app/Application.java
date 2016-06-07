package org.ameet.app;

import org.ameet.akka.AkkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by achaub001c on 6/7/2016.
 * this is the main Spring based application
 */
@SpringBootApplication
@ComponentScan("org.ameet.akka")
public class Application implements CommandLineRunner {
    @Autowired
    private AkkaProcessor akkaProcessor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        akkaProcessor.calculate(4, 10000, 10000);
        System.out.println(".... Second time...\n\n");
        akkaProcessor.calculate(4, 10000, 10000);
    }
}
