package di;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BeanWithDeps {
    private final Dependency1 dependency1;
    private final Dependency2 dependency2;
}
