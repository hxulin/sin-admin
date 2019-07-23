package tech.ldxy.sin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.system.auth.Resources;

import java.lang.reflect.Method;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
//@ActiveProfiles("test")
public class ApplicationTests {

    @Test
    public void contextLoads() {

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);  // 不使用默认的TypeFilter
        provider.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        provider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents("tech.ldxy.sin.system.web.controller");
        beanDefinitionSet.forEach(item -> {
            try {
                Class<?> clazz = Class.forName(item.getBeanClassName());

                RequestMapping annotation1 = clazz.getAnnotation(RequestMapping.class);

                System.out.println("Request Mapping: " +  annotation1);


                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {


                    Resources annotation = method.getAnnotation(Resources.class);
                    System.out.println(item.getBeanClassName() + " --- " + method.getName());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//        System.out.println(beanDefinitionSet);
    }

}
