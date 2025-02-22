package di;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Stores all the necessary information that the application context needs for a bean instance.
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
class BeanInstance<T> {
    private final String name;
    private final T bean;
}
