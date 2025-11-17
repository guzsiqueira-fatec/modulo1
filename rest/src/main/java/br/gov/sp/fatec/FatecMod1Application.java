package br.gov.sp.fatec;



import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
 public class FatecMod1Application {
    private final Environment environment;

    public FatecMod1Application(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(FatecMod1Application.class, args);
    }

    @PostConstruct
    public void testConfig() {
        System.out.println("DB URL = " + environment.getProperty("spring.datasource.url"));
    }
}
