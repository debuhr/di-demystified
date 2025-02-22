package di.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a configuration class. Configuration classes are beans, i.e. an instance of this class will be
 * created and will be accessible in the application context.
 * Additionally, a configuration class is scanned for bean factory methods annotated with {@link Bean @Bean}. The
 * bean factory methods need to return an instance of a bean and will be called by the application context which then
 * registers the beans, making them available to the application.
 * @see di.ApplicationContext
 * @see Bean
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
}
