package di;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

// TODO (jdb): break circular dependencies between BeanCreator and ApplicationContext
class BeanCreator {
    static void instantiateBean(String name, Class<?> clazz)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {

        // TODO (jdb): currently this always uses the first constructor - instead, check if one exists that can be
        //  called with the types we have beans for
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        Object bean = constructor.newInstance(findArguments(constructor).toArray());
        ApplicationContext.register(name, clazz, bean);
    }

    private static @NotNull List<Object> findArguments(Constructor<?> constructor) {
        List<Object> arguments = new ArrayList<>();
        for (Parameter parameter : constructor.getParameters()) {
            Class<?> aClass = extractActualType(parameter);
            List<?> objects = ApplicationContext.findBeans(aClass);


            Type type = parameter.getParameterizedType();
            if (type instanceof ParameterizedType parameterizedType
                && parameterizedType.getRawType().getTypeName().equals("java.util.List")) {
                arguments.add(objects);
            } else {
                arguments.add(objects.getFirst());
            }
        }
        return arguments;
    }

    private static Class<?> extractActualType(Parameter parameter) {
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

