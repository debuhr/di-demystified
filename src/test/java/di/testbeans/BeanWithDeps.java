package di.testbeans;

import di.annotation.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BeanWithDeps {
    private final Dependency1 dependency1;
    private final Dependency2 dependency2;
}
