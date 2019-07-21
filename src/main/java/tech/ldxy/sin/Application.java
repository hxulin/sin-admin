package tech.ldxy.sin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.ldxy.sin.system.context.ApplicationStartedEventListener;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.addListeners(new ApplicationStartedEventListener());
        application.run(args);
    }

}
