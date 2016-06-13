package org.ameet.app;

import org.ameet.akka.AkkaProcessor;
import org.ameet.akka.message.Answer;
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
@ComponentScan({"org.ameet.akka", "org.ameet.app"})
public class Application implements CommandLineRunner {
    @Autowired
    private AkkaProcessor akkaProcessor;
    @Autowired
    private ConfigVars configVars;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Answer a = akkaProcessor.calculate(configVars.getAPP_URL(), 5);
        a.getQuote().stream().forEach(System.out::println);
        System.out.println("################################################");
          a = akkaProcessor.calculate(configVars.getAPP_URL(), 5);
        a.getQuote().stream().forEach(System.out::println);
        akkaProcessor.shutdown();
    }
}
