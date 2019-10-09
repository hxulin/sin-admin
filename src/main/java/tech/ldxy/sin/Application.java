package tech.ldxy.sin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.ldxy.sin.system.context.ApplicationStartedEventListener;

/**
 * 功能描述: 项目启动入口
 *
 * @author hxulin
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.addListeners(new ApplicationStartedEventListener());
        application.run(args);
    }

}
