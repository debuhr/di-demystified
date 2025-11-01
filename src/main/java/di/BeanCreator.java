package di;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Creates beans using the bean classes constructor or a factory method from a configuration class.
 * All the low level concerns of instantiating beans using reflection are contained here.
 */
@RequiredArgsConstructor
class BeanCreator {
    private final Function<Class<?>, List<?>> beanFinder;

    Object createBeanFromFactoryMethod(Object configurationBean, Method method) {
        try {
            // TODO (jdb): handle bean dependencies (arguments of the factory method) the same way as below for
            //  constructors, i.e. by finding the dependencies in the application context.
            return method.invoke(configurationBean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    Object instantiateBean(Class<?> clazz) {
        // TODO (jdb): currently this always uses the first constructor - instead, check if one exists that can be
        //  called with the types we have beans for
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        try {
            return constructor.newInstance(findArguments(constructor).toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private @NotNull List<Object> findArguments(Constructor<?> constructor) {
        List<Object> arguments = new ArrayList<>();
        for (Parameter parameter : constructor.getParameters()) {
            Class<?> clazz = extractActualType(parameter);
            List<?> objects = beanFinder.apply(clazz);

            Type type = parameter.getParameterizedType();
            // TODO (jdb): replace list check with isAssignable
            if (type instanceof ParameterizedType parameterizedType
                && parameterizedType.getRawType().getTypeName().equals("java.util.List")) {
                arguments.add(objects);
            } else {
                arguments.add(objects.get(0));
            }
        }
        return arguments;
    }

    private Class<?> extractActualType(Parameter parameter) {
        // TODO (jdb): is there a better way to get the class from a type (or the other way around and store the type
        //  in the map instead of the class) than Class.forName()?
        try {
            Type type = parameter.getParameterizedType();
            if (type instanceof ParameterizedType parameterizedType) {
                // TODO (jdb): make this work for more complicated generic types, now it always uses the first type
                return Class.forName(parameterizedType.getActualTypeArguments()[0].getTypeName());
            }

            return Class.forName(type.getTypeName());
        } catch (ClassNotFoundException e) {
            // TODO (jdb): better handling, maybe a custom exception that helps the developer
            throw new RuntimeException(e);
        }
    }
}

