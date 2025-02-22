package di.configurationclass;

import di.annotation.Bean;
import di.annotation.Configuration;
import di.testbeans.Dependency1;

@Configuration
public class TestConfiguration {

    @Bean
    public Dependency1 dependency1() {
        return new Dependency1();
    }
}
