package di;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
class BeanInstance<T> {
    private final String name;
    private final T bean;
}
