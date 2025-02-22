package di.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a factory method for a bean in a configuration class. The method will be called by the application context to
 * create an instance of the bean.
 * Dependencies are *not* supported at the moment. The factory method *cannot* have parameters.
 *
 * @see Configuration Configurotion class
 * @see di.ApplicationContext
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
