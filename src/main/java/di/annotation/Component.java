package di.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a bean so that it will be found in a bean scan. The application context scans for beans annotated
 * with this annotation, creates an instance of the bean and adds it to the application context. Dependencies *must* be
 * beans and *must* be declared as arguments in the *first* constructor.
 * Only the first constructor of the component/bean is used.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
}
